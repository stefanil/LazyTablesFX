package org.devel.lazytablesfx.client;

import static org.junit.Assert.assertTrue;
//import static org.loadui.testfx.Assertions.*;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

import org.devel.lazytablesfx.client.controls.LazyListViewB;
import org.devel.lazytablesfx.client.controls.LazyListViewSkinB;
import org.devel.lazytablesfx.client.strategies.RESTfulPersonLoader;
import org.devel.lazytablesfx.model.Person;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class LazyListViewBTest extends GuiTest {

	private static final String ID_LIST = "myLazyListView";

	@Override
	protected Parent getRootNode() {
		// layout container
		BorderPane root = new BorderPane();
		root.setPrefWidth(600);
		root.setPrefHeight(400);

		final LazyListViewB<Person> listView = new LazyListViewB<>();
		listView.setId(ID_LIST);
		listView.setSkin(new LazyListViewSkinB<Person>(listView,
				new RESTfulPersonLoader("http://localhost:9000/", "/people")));
		listView.setNextLoadCellDistance(3);
		root.setCenter(listView);

		return root;
	}
	
	@Test
	public void scrollToLoad() {
		// GIVEN
		click(ID_LIST, MouseButton.PRIMARY);
		// WHEN
		scroll(10, VerticalDirection.DOWN);
		// THEN
		assertTrue(find("#lazy-list-progress-bar").isVisible());
	}

}
