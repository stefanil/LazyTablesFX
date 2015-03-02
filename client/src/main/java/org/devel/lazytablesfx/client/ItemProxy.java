package org.devel.lazytablesfx.client;

import javafx.util.Callback;

/**
 * 
 * @author stefan.illgen
 *
 * @param <S> Subject
 */
public interface ItemProxy<S> {

	public void load(final Integer index,
			final Callback<? super Integer, Void> finishedHandler);

	public boolean isLoaded();
	
	public S getSubject();
	
}
