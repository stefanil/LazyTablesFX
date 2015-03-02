/**
 * 
 */
package org.devel.lazytablesfx.client;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.devel.lazytablesfx.client.controls.LazyListView;
import org.devel.lazytablesfx.client.controls.LazyListViewSkin;
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

		// minimal example (List Bug)
		// final ListView<Person> listView = new ListView<Person>();
		// listView.setItems(FXCollections
		// .observableArrayList(new ArrayList<Person>() {
		// {
		// for (int i = 0; i < 100; i++) {
		// add(new Person("email_"+i));
		// }
		// }
		// }));

		final LazyListView<PersonProxy, Person> listView = new LazyListView<>();
		listView.setFixedCellSize(50);
		listView.setCellFactory(new Callback<ListView<PersonProxy>, ListCell<PersonProxy>>() {
			@Override
			public ListCell<PersonProxy> call(ListView<PersonProxy> list) {
				return new PersonCell();
			}
		});
		listView.setSkin(new LazyListViewSkin<>(listView));
		root.setCenter(listView);
		listView.setItems(FXCollections
				.observableArrayList(new ArrayList<PersonProxy>() {
					private static final long serialVersionUID = -5517985007392561839L;
					{
						for (int i = 0; i < 100; i++) {
							add(new PersonProxy());
						}
					}
				}));

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
