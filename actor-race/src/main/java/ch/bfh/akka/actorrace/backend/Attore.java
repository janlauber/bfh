package ch.bfh.akka.actorrace.backend;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

/**
 * Creates a Attore (Akka Actor) with a defined movement strategy
 */
public abstract class Attore extends AbstractBehavior<Attore.Message> {

	/**
	 * Akka Interface for the messages, the actor can process
	 */
	interface Message { }

	/**
	 * Abstract class for the Attores
	 * @param context is the standard context, given by the create method of akka
	 */
	public Attore(ActorContext<Message> context) {
		super(context);
	}

	/**
	 * Start-Message class for the actor
	 */
	public static class Start implements Message {
		int x;
		int y;
		LabirintoEstremo lab;
		ActorRef<GrossoCervello.Message> rootRef;

		/**
		 * Start-Message constructor for the attore
		 * Defines all necessary links
		 * @param lab is the reference of the labirinto
		 * @param rootRef is the reference of the root actor (GrossoCervello)
		 * @param x is x the coordinate of the actor on the labirinto
		 * @param y is y the coordinate of the actor on the labirinto
		 */
		public Start(LabirintoEstremo lab, ActorRef<GrossoCervello.Message> rootRef, int x, int y) {
			this.lab = lab;
			this.rootRef = rootRef;
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Akka default method for processing messages
	 */
	@Override
	public Receive<Attore.Message> createReceive() {
		return newReceiveBuilder()
				.onMessage(Start.class, this::onStart)
				.build();
	}

	/**
	 * Start function of the actor: starts the movements
	 * @param command is the object of the Message-Start class
	 * @return the Akka Behaviour
	 * @throws InterruptedException when the thread is interrupted
	 */
	protected abstract Behavior<Message> onStart(Start command) throws InterruptedException;;

	/**
	 * Movement strategy of the attore
	 * @throws InterruptedException when the thread is interrupted
	 */
	protected abstract void movement() throws InterruptedException;;
}
