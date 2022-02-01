package ch.bfh.akka.actorrace.backend;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import java.util.Stack;

/**
 * Creates a Attore (Akka Actor) with the algorithm "schneggo"
 */
public class AttoreSchneggo extends Attore {
	private static final int THREAD_SLEEP = 50;
	private LabirintoEstremo lab;
	private LabirintoEstremo localLabirinto;
	private ActorRef<GrossoCervello.Message> rootRef;
	private int currentX;
	private int currentY;

	/**
	 * Akka Create-function for the actor
	 * @return the Akka Behaviour
	 */
	public static Behavior<Message> create() {
		return Behaviors.setup(AttoreSchneggo::new);
	}

	/**
	 * Initializes the attore
	 * @param context is the standard context, given by the create method of akka
	 */
	public AttoreSchneggo(ActorContext<Message> context) {
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
		localLabirinto = new LabirintoEstremo(lab.getX(), lab.getY());
		movement();
		return this;
	}

	/**
	 * Movement strategie of the attore
	 */
	@Override
	public void movement() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Stack<Integer> path = new Stack<Integer>();

		// Start searching for goal
		while (!lab.isGoal(currentX, currentY)) {
			Thread.sleep(THREAD_SLEEP);
			// Console log for debugging

			// Add current position to pathlist
			path.push(currentX);
			path.push(currentY);

			// Add current position to localLabirinto
			localLabirinto.setPath(currentX, currentY);

			if (lab.isGoal(currentX + 1, currentY)) {
				currentX++;
			} else if (lab.isGoal(currentX - 1, currentY)) {
				currentX--;
			} else if (lab.isGoal(currentX, currentY + 1)) {
				currentY++;
			} else if (lab.isGoal(currentX, currentY - 1)) {
				currentY--;
			} else {
				// if currentX + 1 is not a wall or path
				if (!lab.isWall(currentX + 1, currentY) && !localLabirinto.isPath(currentX + 1, currentY)) {
					currentX += 1;
				} else if (!lab.isWall(currentX, currentY + 1) && !localLabirinto.isPath(currentX, currentY + 1)) {
					// if currentY + 1 is not a wall or path
					currentY += 1;
				} else if (!lab.isWall(currentX - 1, currentY) && !localLabirinto.isPath(currentX - 1, currentY)) {
					// if currentX - 1 is not a wall or path
					currentX -= 1;
				} else if (!lab.isWall(currentX, currentY - 1) && !localLabirinto.isPath(currentX, currentY - 1)) {
					// if currentY - 1 is not a wall or path
					currentY -= 1;
				} else {
					// Otherwise go one step back -> pop twice
					currentY = path.pop();
					currentX = path.pop();
					if (!path.isEmpty()) {
						currentY = path.pop();
						currentX = path.pop();
					} else {
						// If path is empty, goal is not found
						System.out.println("Goal not found");
						currentY = -1;
						currentX = -1;
						rootRef.tell(new GrossoCervello.FoundGoal(currentX, currentY,
								startTime, getContext().getSelf()));
						return;
					}
				}
			}

			// Tell GrossoCervello to update the position
			rootRef.tell(new GrossoCervello.UpdatePosition(currentX, currentY, getContext().getSelf()));
		}
		// Tell GrossoCervello that goal is found
		rootRef.tell(new GrossoCervello.FoundGoal(currentX, currentY, startTime, getContext().getSelf()));
	}
}
