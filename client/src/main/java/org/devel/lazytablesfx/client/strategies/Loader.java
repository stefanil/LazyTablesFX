package org.devel.lazytablesfx.client.strategies;

import javafx.collections.ObservableList;

public interface Loader<T> {

	public ObservableList<T> next();
	
}
