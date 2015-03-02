package org.devel.lazytablesfx.client.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;

import org.devel.lazytablesfx.client.ItemProxy;

public class LazyListView<T extends ItemProxy<S>, S> extends ListView<T> {

	public LazyListView() {
		super();
	}

	public LazyListView(ObservableList<T> items) {
		super(items);
	}

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
					return LazyListView.this;
				}

				@Override
				public String getName() {
					return "progress";
				}
			};
		}
		return progress;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new LazyListViewSkin<>(this);
	}

}
