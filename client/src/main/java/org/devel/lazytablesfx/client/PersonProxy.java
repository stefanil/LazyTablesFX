package org.devel.lazytablesfx.client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.util.Callback;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.devel.lazytablesfx.model.Person;

public class PersonProxy extends Person {

	private Person person;

	/*
	 * Callback typing:
	 * 
	 * Parameter Type (Integer): Index to be updated
	 * 
	 * Return Type (Void): null to be returned to this proxy
	 */
	public void load(final Integer index,
			final Callback<? super Integer, Void> finishedHandler) {

		if (index == null)
			throw new IllegalArgumentException(
					"Parameter index must not be null.");
		if (finishedHandler == null)
			throw new IllegalArgumentException(
					"Parameter finishedHandler must not be null.");

		// load data from server
		Thread thread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target("http://localhost:9000/")
						.path("/people");
				person = target.path("email_" + index)
						.request(MediaType.APPLICATION_JSON).get(Person.class);
				Platform.runLater(() -> finishedHandler.call(index));
				return null;
			}
		});
		thread.start();

	}

	public boolean isLoaded() {
		return person != null;
	}

	public Person getPerson() {
		return person;
	}

}
