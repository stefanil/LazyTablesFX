package org.devel.lazytablesfx.client.controls;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;

import com.sun.javafx.scene.control.skin.ListViewSkin;

@SuppressWarnings("restriction")
public class LazyListViewSkin<T> extends ListViewSkin<T> {

	private double screenY, totalY = 0.0d;
	
	public LazyListViewSkin(ListView<T> listView) {
		super(listView);
//		getSkinnable().setOnScroll(new EventHandler<ScrollEvent>() {
//			@Override
//			public void handle(ScrollEvent event) {
//				double deltaY = event.getDeltaY();
//				flow.getVbar().getVisibleAmount();
//			}
//		});
	}

}
