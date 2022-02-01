/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
module ch.bfh.piechart {
	exports ch.bfh.piechart;

	opens ch.bfh.piechart;
	opens ch.bfh.piechart.ui;
	opens ch.bfh.piechart.model;
	opens ch.bfh.piechart.datalayer;
	opens ch.bfh.matrix;

	requires javafx.base;
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires java.instrument;
	requires java.sql;
}
