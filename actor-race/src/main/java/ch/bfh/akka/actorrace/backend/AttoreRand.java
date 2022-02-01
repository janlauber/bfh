package ch.bfh.akka.actorrace.backend;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import java.util.Random;

/**
 * Creates a Attore (Akka Actor) with the algorithm "random"
 */
public class AttoreRand extends Attore {
	private static final int THREAD_SLEEP = 50;
	private LabirintoEstremo lab;
	private ActorRef<GrossoCervello.Message> rootRef;
	private int currentX;
	private int currentY;

	/**
	 * Akka Create-function for the actor
	 * @return the Akka Behaviour
	 */
	public static Behavior<Message> create() {
		return Behaviors.setup(AttoreRand::new);
	}

	/**
	 * Initializes the attore
	 * @param context is the standard context, given by the create method of akka
	 */
	public AttoreRand(ActorContext<Message> context) {
		super(context);
	}

	/**
	 * Start function of the attore, starts the movements
	 * @param command is the object of the Message-Start class
	 * @return the Akka Behaviour
	 */
	@Override
	public Behavior<Message> onStart(Start command) throws InterruptedException {
		// Define local variables and start searching for goal
		this.lab = command.lab;
		this.rootRef = command.rootRef;
		this.currentX = command.x;
		this.currentY = command.y;
		movement();
		return this;
	}

	/**
	 * Movement strategie of the attore
	 */
	@Override
	public void movement() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Random r = new Random();
		int random;

		// loop until the goal is found
		while (!lab.isGoal(currentX, currentY)) {
			Thread.sleep(THREAD_SLEEP);
			random = r.nextInt(4);
			//System.out.println("X = " + currentX + " Y = " + currentY);

			if (lab.isGoal(currentX + 1, currentY)) {
				currentX++;
			} else if (lab.isGoal(currentX - 1, currentY)) {
				currentX--;
			} else if (lab.isGoal(currentX, currentY + 1)) {
				currentY++;
			} else if (lab.isGoal(currentX, currentY - 1)) {
				currentY--;
			} else {
				// make the move if the spot is not a wall
				switch (random) {
				case 0:
					if (!lab.isWall(currentX + 1, currentY)) {
						currentX += 1;
					}
					break;
				case 1:
					if (!lab.isWall(currentX - 1, currentY)) {
						currentX -= 1;
					}
					break;
				case 2:
					if (!lab.isWall(currentX, currentY + 1)) {
						currentY += 1;
					}
					break;
				case 3:
					if (!lab.isWall(currentX, currentY - 1)) {
						currentY -= 1;
					}
					break;
				default:
					break;
				}
			}

			// Tell GrossoCervello to update the position
			rootRef.tell(new GrossoCervello.UpdatePosition(currentX, currentY, getContext().getSelf()));
		}
		// Tell GrossoCervello that goal is found
		rootRef.tell(new GrossoCervello.FoundGoal(currentX, currentY, startTime, getContext().getSelf()));
	}
}
