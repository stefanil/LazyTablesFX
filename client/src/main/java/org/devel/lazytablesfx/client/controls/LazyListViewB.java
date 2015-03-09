package org.devel.lazytablesfx.client.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class LazyListViewB<T> extends ListView<T> {

	public LazyListViewB() {
		super();
	}

	public LazyListViewB(ObservableList<T> items) {
		super(items);
	}

	/* PROGRESS */

	public static final double INDETERMINATE_PROGRESS = -1;

	private DoubleProperty progress;

	public final void setProgress(double value) {
		progressProperty().set(value);
	}

	public final double getProgress() {
		return progress == null ? INDETERMINATE_PROGRESS : progress.get();
	}

	public final DoubleProperty progressProperty() {
		if (progress == null) {
			progress = new DoublePropertyBase(-1.0) {
				@Override
				protected void invalidated() {
					// setIndeterminate(getProgress() < 0.0);
				}

				@Override
				public Object getBean() {
					return LazyListViewB.this;
				}

				@Override
				public String getName() {
					return "progress";
				}
			};
		}
		return progress;
	}

	/* NEXT LOAD CELL DISTANCE */

	private static final int NEXT_LOAD_CELL_DISTANCE_DEFAULT_VALUE = 0;

	private IntegerProperty nextLoadCellDistance;

	public final void setNextLoadCellDistance(int value) {
		nextLoadCellDistanceProperty().set(value);
	}

	public final int getNextLoadCellDistance() {
		return nextLoadCellDistance == null ? NEXT_LOAD_CELL_DISTANCE_DEFAULT_VALUE
				: nextLoadCellDistance.get();
	}

	/**
	 * Cell index from where to load next items:
	 * 
	 * <ul>
	 * 	<li>0 .. start to load when the last cell gets visible</li>
	 * 	<li>1 .. start to load when the cell next to the last cell gets visible</li>
	 * 	<li>aso.</li>
	 * </ul>
	 * 
	 * @return
	 */
	public final IntegerProperty nextLoadCellDistanceProperty() {
		if (nextLoadCellDistance == null) {
			nextLoadCellDistance = new SimpleIntegerProperty(
					NEXT_LOAD_CELL_DISTANCE_DEFAULT_VALUE);
		}
		return nextLoadCellDistance;
	}

	// /* PRIVATE */
	//
	// @Override
	// protected Skin<?> createDefaultSkin() {
	// return new LazyListViewSkinB<>(this);
	// }

}
