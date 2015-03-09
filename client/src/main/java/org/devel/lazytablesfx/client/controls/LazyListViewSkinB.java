package org.devel.lazytablesfx.client.controls;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;

import org.apache.log4j.Logger;
import org.devel.lazytablesfx.client.strategies.LazyLoader;

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

	private LazyLoader<T> loader;

	private double lastPosition = 0.0d;

	public LazyListViewSkinB(ListView<T> listView, LazyLoader<T> loader) {
		super(listView);
		if (loader == null)
			throw new IllegalArgumentException(
					"Parameter loader must not be null.");
		this.loader = loader;
		// create nodes
		progressBar = new ProgressBar(0.5);
		progressBar.setId("lazy-list-progress-bar");
		progressBar.progressProperty().bind(
				((LazyListViewB<T>) getSkinnable()).progressProperty());
		getChildren().add(progressBar);

		// ####### handle scroll down #######

		// won't handle any scroll event (including drag events to move the
		// list)
		// getSkinnable().addEventFilter(ScrollEvent.ANY,
		// event -> onScroll());

		// workaround
		for (Node node : getSkinnable().lookupAll(".scroll-bar")) {
			if (node instanceof ScrollBar) {
				final ScrollBar scrollBar = (ScrollBar) node;
				if (scrollBar.getOrientation().equals(Orientation.VERTICAL)) {
					scrollBar.valueProperty().addListener(
							(obs, oldV, newV) -> onScroll());
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
		} else {
			super.layoutChildren(x, y, w, h);
			progressBar.resizeRelocate(x, h - 18, 0, 0);
		}
	}

	private void onScroll() {
		/*
		 * If there is a fixed size constraint for the list, calculation is
		 * possible. But if not, we cannot assume a position. Thus we make use
		 * of the flow to detect visibility state for a given cell index. If 
		 * the cell is about to get visible, then reload.
		 */
		boolean mustLoad = false;
		LazyListViewB<T> listView = (LazyListViewB<T>) getSkinnable();
		int nextLoadIndex = getItemCount() - (listView.getNextLoadCellDistance() + 1);
		mustLoad = flow.getVisibleCell(nextLoadIndex) != null;

		double newPosition = flow.getPosition();
		if (!loading && lastPosition < newPosition && mustLoad) {
			loading = true;
			getSkinnable().requestLayout();
			loadNextListViewItems();
		}
		lastPosition = newPosition;
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

		Thread t = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				// TODO stefan - remove (just 4 the showcase)
				if (listView.getItems().size() != 0)
					Thread.sleep(2000);

				ObservableList<T> newItems = loader.next();

				Platform.runLater(() -> {
					/*
					 * Workaround for preventing the list from jumping to the end. When
					 * the list is already at the end before adding new items, we want to
					 * show the currently visible cell. 
					 */
					flow.adjustPixels(-0.1);
					
					// add items
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

					// listView.requestLayout();

					// superfluous
					// if (lastVisibleCell != null)
					// flow.show(lastVisibleCell);
				});

				return null;
			}
		});
		t.start();

	}
}
