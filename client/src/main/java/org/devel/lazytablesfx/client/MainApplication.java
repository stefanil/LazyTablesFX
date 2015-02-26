/**
 * 
 */
package org.devel.lazytablesfx.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author stefan.illgen
 *
 */
public class MainApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = new Scene(createNodes());
		// scene.getStylesheets().add(getClass().getResource(".").toExternalForm()
		// + getClass().getSimpleName() + ".css");
		stage.setScene(scene);
		stage.setTitle(getClass().getName());

		stage.show();

		stage.setMinWidth(600);
		stage.setMinHeight(300);
	}

	private Parent createNodes() {

		// layout container
		BorderPane root = new BorderPane();
		root.setPrefWidth(600);
		root.setPrefHeight(400);

		// root.setCenter(new ListView<Person>());

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

	// private void loadFXML() {
	//
	// URL url = ".fxml";
	// FXMLLoader fxmlLoader = new FXMLLoader(url);
	//
	// fxmlLoader.setRoot(this);
	// fxmlLoader.setController(this);
	//
	// try {
	// fxmlLoader.load();
	// } catch (IOException exception) {
	// throw new RuntimeException(exception);
	// }
	// }

}
