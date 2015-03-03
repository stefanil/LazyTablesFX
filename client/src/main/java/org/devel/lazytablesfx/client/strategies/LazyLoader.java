package org.devel.lazytablesfx.client.strategies;

import javafx.collections.ObservableList;

public interface LazyLoader<T> {

	public ObservableList<T> next();
	
}
