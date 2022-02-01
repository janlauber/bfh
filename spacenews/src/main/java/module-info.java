/**
 * Project and Training 2: Space News - Computer Science, Berner Fachhochschule
 *
 * Specification of required Java modules. Add further entries if necessary.
 */
module spacenewsfx {
	// JavaFX
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;

	// FasterXML JSON library
	requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports ch.bfh.spacenews;
	exports ch.bfh.spacenews.model;
 }
