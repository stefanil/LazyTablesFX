package org.devel.lazytablesfx.client.controls;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.devel.lazytablesfx.client.ItemProxy;
import org.devel.lazytablesfx.client.PersonCell;

import com.sun.javafx.scene.control.skin.ListViewSkin;

@SuppressWarnings("restriction")
public class LazyListViewSkin<T extends ItemProxy<S>, S> extends
		ListViewSkin<T> {

	private static final Logger LOGGER = Logger
			.getLogger(LazyListViewSkin.class);

	private ProgressBar progressBar;
	private int progressedItems;

	@SuppressWarnings("unchecked")
	public LazyListViewSkin(ListView<T> listView) {
		super(listView);
		progressBar = new ProgressBar(0.5);
		progressBar.progressProperty().bind(
				((LazyListView<T, S>) getSkinnable()).progressProperty());
		getChildren().add(progressBar);

	}

	@Override
	protected void layoutChildren(double x, double y, double w, double h) {
		super.layoutChildren(x, y, w, h - 18);
		progressBar.resizeRelocate(x, h - 18, w, 18);
	}

	@Override
	protected void handleControlPropertyChanged(String p) {
		super.handleControlPropertyChanged(p);
		if ("ITEMS".equals(p)) {
			reloadListViewItems();
		}
	}

	private void reloadListViewItems() {
		Thread t = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				ObservableList<T> items = getSkinnable().getItems();
				progressedItems = 0;
				for (int i = 0; i < items.size(); i++) {
					final T item = items.get(i);
					item.load(i, new Callback<Integer, Void>() {
						@SuppressWarnings("unchecked")
						@Override
						public Void call(final Integer index) {
							LOGGER.info("index " + index + " completed");
							final int j = index.intValue();
							// Platform.runLater(() -> {
							Platform.runLater(new Runnable() {
								public void run() {
									progressedItems++;
									((LazyListView<T, S>) getSkinnable())
											.setProgress(progressedItems
													/ items.size());
//									if (j == 0) {
										
										// approach A (works not)
//										ListCell<T> cell = flow.getCell(index);
//										if (!cell.isEmpty())
//											cell.requestLayout();
//										getSkinnable().requestLayout();
										
										// approach B (works not)
										// updateListViewItems();
										// approach C
//										updateRowCount();
//										getSkinnable().requestLayout();
										
										// approach D
										// works not
//										flow.reconfigureCells();
										// works
//										flow.recreateCells();
										// works
										flow.rebuildCells();
										// works not
//										flow.requestCellLayout();
										// works not
//										flow.setCellDirty(0);
//									}
								}
							});
							return null;
						}
					});
				}
				return null;
			}
		});
		t.start();
	}

}
