package org.devel.lazytablesfx.client;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ListBugApplication extends Application {

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
		
		BorderPane root = new BorderPane();
		
		final ListView<String> listView = new ListView<>();
		listView.setItems(FXCollections
				.observableArrayList(new ArrayList<String>() {
					private static final long serialVersionUID = -6530402202625524689L;
					{
						for (int i = 0; i < 100; i++) {
							add(new String("email_" + i));
						}
					}
				}));
		root.setCenter(listView);
		
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
