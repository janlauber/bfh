package ch.bfh.akka.actorrace.backend;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import ch.bfh.akka.actorrace.frontend.CentroDiControllo;
import ch.bfh.akka.actorrace.frontend.Controllore;
import java.util.Random;

/**
 * Creates a GrossoCervollo (Akka Actor) which spawn all the game actors (Attores).
 */
public class GrossoCervello extends AbstractBehavior<GrossoCervello.Message> {
	private static final int MILISECONDS_TO_SECONDS = 1000;
	private final ActorRef<Message> grossoCervello;
	private final ActorRef<Attore.Message> attoreRand;
	private final ActorRef<Attore.Message> attoreSchneggo;
	private static LabirintoEstremo lab;
	private int randX;
	private int randY;
	private int schneggoX;
	private int schneggoY;

	/**
	 * Akka Interface for the messages, the actor can process
	 */
	public interface Message { }

	/**
	 * Akka Create-function for the actor
	 * @return the Akka Behaviour
	 */
	public static Behavior<Message> create() {
		return Behaviors.setup(GrossoCervello::new);
	}

	/**
	 * Initialize the GrossoCervello
	 * @param context is the standard context, given by the create method of akka
	 */
	public GrossoCervello(ActorContext<Message> context) {
		super(context);
		grossoCervello = getContext().getSelf();
		attoreRand = context.spawn(AttoreRand.create(), "attoreRand");
		attoreSchneggo = context.spawn(AttoreSchneggo.create(), "attoreSchneggo");
	}

	/**
	 * Stop-Message class for the actor
	 */
	public static class Stop implements Message { }

	/**
	 * StartAttores-Message class for the actor
	 */
	public static class StartAttores implements Message {
		/**
		 * Initialization of the StartAttores
		 * @param labirinto is the reference to the labirinto
		 */
		public StartAttores(LabirintoEstremo labirinto) {
			lab = labirinto;
		}
	}

	/**
	 * UpdatePosition-Message class for the actor
	 */
	public static class UpdatePosition implements Message {
		int x;
		int y;
		ActorRef<Attore.Message> ref;

		/**
		 * Initialization of the UpdatePosition
		 * @param x is the x coordinate in the labirinto
		 * @param y is the y coordinate in the labirinto
		 * @param ref is the reference to the specific attore
		 */
		public UpdatePosition(int x, int y, ActorRef<Attore.Message> ref) {
			this.x = x;
			this.y = y;
			this.ref = ref;
		}
	}

	/**
	 * FoundGoal-Message class for the actor
	 */
	public static class FoundGoal implements Message {
		int x;
		int y;
		long startTime;
		ActorRef<Attore.Message> ref;

		/**
		 * Initialization of the FoundGoal
		 * @param x is the x coordinate in the labirinto
		 * @param y is the y coordinate in the labirinto
		 * @param startTime is the time, when the movements are started
		 * @param ref is the reference to the specific attore
		 */
		public FoundGoal(int x, int y, long startTime, ActorRef<Attore.Message> ref) {
			this.x = x;
			this.y = y;
			this.startTime = startTime;
			this.ref = ref;
		}
	}

	/**
	 * Get a random start position on the labirinto
	 */
	private int[] getRandomStartPosition() {
		Random rand = new Random();
		int startRandX = (rand.nextInt(lab.getX() - 2) + 1);
		int startRandY = (rand.nextInt(lab.getY() - 2) + 1);

		while (lab.getLabirinto()[startRandX][startRandY] == 1) {
			startRandX = (int) (Math.random() * lab.getX());
			startRandY = (int) (Math.random() * lab.getY());
		}
		return new int[]{startRandX, startRandY};
	}

	/**
	 * Akka default method for processing messages
	 */
	@Override
	public Receive<Message> createReceive() {
		return newReceiveBuilder()
				.onMessage(GrossoCervello.StartAttores.class, this::onStartAttores)
				.onMessage(GrossoCervello.UpdatePosition.class, this::onUpdatePosition)
				.onMessage(GrossoCervello.FoundGoal.class, this::onFoundGoal)
				.onMessage(GrossoCervello.Stop.class, this::onStop)
				.build();
	}

	/**
	 * StartAttores function of the actor: starts each attore
	 * @param command is the object of the Message-StartAttores class
	 * @return the Akka Behaviour
	 */
	private Behavior<Message> onStartAttores(StartAttores command) {
		// define random start position which is not on the wall
		int[] randStartPosition = getRandomStartPosition();
		attoreRand.tell(new Attore.Start(lab, grossoCervello, randStartPosition[0], randStartPosition[1]));
		randStartPosition = getRandomStartPosition();
		attoreSchneggo.tell(new Attore.Start(lab, grossoCervello, randStartPosition[0], randStartPosition[1]));
		return this;
	}

	/**
	 * UpdatePosition function of the actor: updates the fxml view by calling the needed controller function
	 * @param command is the object of the Message-UpdatePosition class
	 * @return the Akka Behaviour
	 */
	private Behavior<Message> onUpdatePosition(UpdatePosition command) {
		if (attoreRand.path().name() == command.ref.path().name()) {
			randX = command.x;
			randY = command.y;
		} else if (attoreSchneggo.path().name() == command.ref.path().name()) {
			schneggoX = command.x;
			schneggoY = command.y;
		}
		// update view with controller function
		Controllore.getInstance().update(randX, randY, schneggoX, schneggoY);
		return this;
	}

	/**
	 * FoundGoal function of the actor: process if a attore reach the goal
	 * @param command is the object of the Message-FoundGoal class
	 * @return the Akka Behaviour
	 */
	private Behavior<Message> onFoundGoal(FoundGoal command) {

		long finishTime = System.currentTimeMillis();
		long totalTime = (finishTime - command.startTime) / MILISECONDS_TO_SECONDS;

		if (command.x == -1 && command.y == -1) {
			// ms to sec
			long time = (System.currentTimeMillis() - command.startTime) / MILISECONDS_TO_SECONDS;
			System.out.println("Attore " + command.ref.path().name() + " didn't found goal in " + time + " sec");
			CentroDiControllo.getInstance().result(command.ref.path().name(), totalTime, -1);
		} else {
			long time = (System.currentTimeMillis() - command.startTime) / MILISECONDS_TO_SECONDS;
			System.out.println("Attore " + command.ref.path().name() + " found goal in " + time + " sec");
			CentroDiControllo.getInstance().result(command.ref.path().name(), totalTime, 1);
		}

		// terminate all attores
		getContext().stop(command.ref);
		return this;
	}

	/**
	 * Stop function of the actor: stops all child-actors and itself
	 * @param command is the object of the Message-Stop class
	 * @return the Akka Behaviour
	 */
	private Behavior<Message> onStop(Stop command) {
		getContext().stop(attoreRand);
		getContext().stop(attoreSchneggo);
		getContext().getSystem().terminate();
		return this;
	}
}

