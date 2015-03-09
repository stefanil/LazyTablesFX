package org.devel.lazytablesfx.client.strategies;

import javafx.collections.ObservableList;

public interface LazyLoader<T> {

	/**
	 * Load next N items of type T, where N is the amount of items to be loaded.
	 * If N is larger then the real amount of remaining items, then just those
	 * get returned.
	 * 
	 * @param size
	 * @return
	 */
	public ObservableList<T> next(/*int amount*/);

}
