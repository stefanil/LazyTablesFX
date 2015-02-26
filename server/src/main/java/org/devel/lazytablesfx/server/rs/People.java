package org.devel.lazytablesfx.server.rs;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.devel.jerseyfx.common.model.Person;
import org.devel.jerseyfx.common.rs.IPeople;
import org.devel.lazytablesfx.server.services.PeopleService;

public class People implements IPeople {

	private static final Logger logger = Logger.getLogger(People.class);

	private PeopleService peopleService = PeopleService.getInstance();

	// ########################### CRUD ###########################
	
	@Override
	public Response addPerson(
			// final UriInfo uriInfo,
			final String email,
			final String firstName, 
			final String lastName) {
		peopleService.addPerson(email, firstName, lastName);
		return Response.ok().build();
	}

	@Override
	public Collection<Person> getPeople(final int page) {
		return peopleService.getPeople(page, 100000);
	}

	@Override
	public Person getPerson(final String email) {
		return peopleService.getByEmail(email);
	}

	@Override
	public Person updatePerson(final String email, final String firstName,
			final String lastName) {
		final Person person = peopleService.getByEmail(email);
		if (firstName != null) {
			person.setFirstName(firstName);
		}
		if (lastName != null) {
			person.setLastName(lastName);
		}
		return person;
	}

	@Override
	public Response deletePerson(final String email) {
		peopleService.removePerson(email);
		return Response.ok().build();
	}

}
