/**
 * 
 */
package org.devel.lazytablesfx.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.devel.lazytablesfx.client.controls.LazyListViewB;
import org.devel.lazytablesfx.client.controls.LazyListViewSkinB;
import org.devel.lazytablesfx.client.strategies.RESTfulPersonLoader;
import org.devel.lazytablesfx.model.Person;

/**
 * @author stefan.illgen
 *
 */
public class MainApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Scene scene = new Scene(createNodes());
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

		final LazyListViewB<Person> listView = new LazyListViewB<>();
		listView.setSkin(new LazyListViewSkinB<Person>(listView,
				new RESTfulPersonLoader("http://localhost:9000/", "/people")));
		listView.setNextLoadCellDistance(3);
		root.setCenter(listView);

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
