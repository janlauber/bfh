/**
 * modul actorrace
 */
module actorrace {
	// Akka
	requires akka.actor.typed;
	requires akka.actor;

	// JavaFX
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;

	exports ch.bfh.akka.actorrace.backend;
	exports ch.bfh.akka.actorrace.frontend;

	opens ch.bfh.akka.actorrace.frontend to javafx.fxml;
}
