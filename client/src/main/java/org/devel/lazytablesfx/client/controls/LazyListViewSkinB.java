package org.devel.lazytablesfx.client.controls;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;

import org.apache.log4j.Logger;
import org.devel.lazytablesfx.client.strategies.Loader;

import com.sun.javafx.scene.control.skin.ListViewSkin;

@SuppressWarnings("restriction")
public class LazyListViewSkinB<T> extends ListViewSkin<T> {

	private static final Logger LOGGER = Logger
			.getLogger(LazyListViewSkinB.class);

	private ProgressBar progressBar;

	/*
	 * Flag for indicating that reloading process has been started.
	 */
	private boolean loading;

	private Loader<T> loader;

	private double oldPosition = 0.0d;

	public LazyListViewSkinB(ListView<T> listView, Loader<T> loader) {
		super(listView);
		if (loader == null)
			throw new IllegalArgumentException(
					"Parameter loader must not be null.");
		this.loader = loader;
		// create nodes
		progressBar = new ProgressBar(0.5);
		progressBar.progressProperty().bind(
				((LazyListViewB<T>) getSkinnable()).progressProperty());
		getChildren().add(progressBar);
		
		// ####### handle scroll down #######
		
		// only works some times
//		getSkinnable().addEventFilter(ScrollEvent.ANY,
//				event -> onScroll());
		// dirty workaround
		for (Node node: getSkinnable().lookupAll(".scroll-bar")) {
			if(node instanceof ScrollBar) {
				final  ScrollBar scrollBar = (ScrollBar) node;
				if(scrollBar.getOrientation().equals(Orientation.VERTICAL)) {
					scrollBar.valueProperty().addListener((obs, oldV, newV)->onScroll());
					break;
				}
			}
		}

		// initiate first load
		loadNextListViewItems();
	}

	@Override
	protected void layoutChildren(double x, double y, double w, double h) {
		if (loading) {
			super.layoutChildren(x, y, w, h - 18);
			progressBar.resizeRelocate(x, h - 18, w, 18);
		} else
			super.layoutChildren(x, y, w, h);
	}

	private void onScroll() {
		double newPosition = flow.getPosition();
		if (oldPosition  < newPosition && newPosition > 0.98) {
			loading = true;
			getSkinnable().requestLayout();
			loadNextListViewItems();
		}
		oldPosition = newPosition;
	}

	// @Override
	// protected void handleControlPropertyChanged(String p) {
	// super.handleControlPropertyChanged(p);
	// if ("ITEMS".equals(p)) {
	// loadNextListViewItems();
	// }
	// }

	private void loadNextListViewItems() {

		LazyListViewB<T> listView = (LazyListViewB<T>) getSkinnable();
		listView.setProgress(-1);

		ListCell<T> lastVisibleCell = flow.getLastVisibleCell();

		// Thread t = new Thread(new Task<Void>() {
		// @Override
		// protected Void call() throws Exception {

		ObservableList<T> newItems = loader.next();

		// Platform.runLater(()->{

		loading = false;
		listView.getItems().addAll(newItems);

		// approach A (works not)
		// ListCell<T> cell = flow.getCell(index);
		// if (!cell.isEmpty())
		// cell.requestLayout();
		// getSkinnable().requestLayout();

		// approach B (works not)
		// updateListViewItems();
		// approach C
		// updateRowCount();
		// getSkinnable().requestLayout();

		// approach D
		// works not
		// flow.reconfigureCells();
		// works
		// flow.recreateCells();
		// works
		// flow.rebuildCells();
		// works not
		// flow.requestCellLayout();
		// works not
		// flow.setCellDirty(0);
		// });
		// return null;
		// }
		// });
		// t.start();

		// listView.requestLayout();
		
		if(lastVisibleCell != null)
			flow.show(lastVisibleCell);
	}
}
