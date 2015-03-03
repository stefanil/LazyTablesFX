package org.devel.lazytablesfx.client.strategies;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.devel.lazytablesfx.model.Person;

public class PersonLoader implements LazyLoader<Person> {

	private String target;
	private String path;
	private int page = 0;

	public PersonLoader(String target, String path) {
		if (target == null || target.trim().isEmpty())
			throw new IllegalArgumentException(
					"Parameter target must not be null.");
		this.target = target;
		if (path == null || path.trim().isEmpty())
			throw new IllegalArgumentException(
					"Parameter path must not be null.");
		this.path = path;
	}

	@Override
	public ObservableList<Person> next() {

		List<Person> result = null;

		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(this.target).path(this.path);
			// TODO stefan - stays domain specific
			result = target.queryParam("page", ++page )
					.request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<Person>>() {});
		} catch (ResponseProcessingException e) {
			e.printStackTrace();
		}
		
		return result == null ? FXCollections.observableArrayList()
				: FXCollections.observableArrayList(result);
	}

}
