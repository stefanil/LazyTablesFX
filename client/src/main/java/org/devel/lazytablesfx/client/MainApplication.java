/**
 * 
 */
package org.devel.lazytablesfx.client;

import java.util.ArrayList;
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

		final ListView<PersonProxy> listView = new ListView<PersonProxy>();
		listView.setCellFactory(new Callback<ListView<PersonProxy>, ListCell<PersonProxy>>() {
			@Override
			public ListCell<PersonProxy> call(ListView<PersonProxy> list) {
				return new PersonCell();
			}
		});
//		listView.setSkin(new LazyListViewSkin<>(listView));
		root.setCenter(listView);

		listView.setItems(FXCollections
				.observableArrayList(new ArrayList<PersonProxy>() {
					private static final long serialVersionUID = -5517985007392561839L;
					{
						for(int i=0; i<100; i++) {
							add(new PersonProxy());
						}
					}
				}));

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
