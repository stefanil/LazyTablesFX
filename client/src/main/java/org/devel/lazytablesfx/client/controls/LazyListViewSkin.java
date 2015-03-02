package org.devel.lazytablesfx.client.controls;

import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import com.sun.javafx.scene.control.skin.ListViewSkin;

@SuppressWarnings("restriction")
public class LazyListViewSkin<T> extends ListViewSkin<T> {

	private ProgressBar progressBar;

	public LazyListViewSkin(ListView<T> listView) {
		super(listView);
		progressBar = new ProgressBar(0.5);
		progressBar.progressProperty().bind(
				((LazyListView<T>) getSkinnable()).progressProperty());
		getChildren().add(progressBar);
	}
	
	@Override
	protected void layoutChildren(double x, double y, double w, double h) {
		super.layoutChildren(x, y, w, h-18);
		progressBar.resizeRelocate(x, h-18, w, 18);
	}	

}
