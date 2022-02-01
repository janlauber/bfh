/*
 * Project and Training 2: Space News Controller - Computer Science, Berner Fachhochschule
 */

package ch.bfh.spacenews;

import ch.bfh.spacenews.model.Article;
import ch.bfh.spacenews.model.SystemInitializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for the main view.
 */
public class Controller {

	/**
	 * The table view.
	 */
	@FXML
	public TableView<Article> table;
	/**
	 * The table column for the title.
	 */
	public TableColumn<Article, String> title;
	/**
	 * The table column for the description.
	 */
	public TableColumn<Article, String> summary;
	/**
	 * The table column for the date.
	 */
	public TableColumn<Article, String> date;

	/**
	 * Initializes the controller class. This method is automatically called
	 */
	public final ObservableList<Article> data = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class. This method is automatically called
	 */
	@FXML
	public void initialize() {

		SystemInitializer systemInitializer = new SystemInitializer();
		data.addAll(systemInitializer.getArticles());
		title.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
		summary.setCellValueFactory(new PropertyValueFactory<Article, String>("summary"));
		date.setCellValueFactory(new PropertyValueFactory<Article, String>("publishedAt"));

		table.setItems(data);
	}

	/**
	 * Handles the event when the user clicks the "Refresh" button.
	 * @param actionEvent The event that is triggered when the user clicks the "Refresh" button.
	 */
	public void refresh(ActionEvent actionEvent) {
		data.clear();
		System.out.println("Refreshing Data...");
		initialize();
	}
}
