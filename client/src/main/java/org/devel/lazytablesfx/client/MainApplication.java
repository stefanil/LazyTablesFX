/**
 * 
 */
package org.devel.lazytablesfx.client;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.devel.jerseyfx.common.model.Person;
import org.devel.lazytablesfx.client.controls.LazyListViewSkin;

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

		final ListView<Person> listView = new ListView<Person>();
		listView.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {
			@Override
			public ListCell<Person> call(ListView<Person> list) {
				return new PersonCell();
			}
		});
		listView.setSkin(new LazyListViewSkin<>(listView));
		root.setCenter(listView);

		// load data from server
		Thread thread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target("http://localhost:9000/")
						.path("/people");
				List<Person> people = target.queryParam("page", 1)
						// .path("stefan.illgen@mail.com")
						.request(MediaType.APPLICATION_JSON)
						.get(new GenericType<List<Person>>() {
						});
				Platform.runLater(() -> loadListData(people));
				return null;
			}

			private void loadListData(List<Person> people) {
				listView.setBackground(new Background(new BackgroundFill(
						Color.AQUA, new CornerRadii(5), new Insets(5))));
				listView.setItems(FXCollections.observableArrayList(people));
			}
		});
		thread.start();

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
