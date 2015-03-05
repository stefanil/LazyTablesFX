package org.devel.lazytablesfx.server.rs;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.devel.lazytablesfx.model.Person;
import org.devel.lazytablesfx.rs.IPeople;
import org.devel.lazytablesfx.server.services.PeopleService;

public class People implements IPeople {

	private static final Logger logger = Logger.getLogger(People.class);

	private PeopleService peopleService = PeopleService.getInstance();

	// private ObjectMapper objectMapper;

	// public People() {
	// objectMapper = new ObjectMapper();
	// }

	// ########################### CRUD ###########################

	@Override
	public Response addPerson(
	// final UriInfo uriInfo,
			final String email, final String firstName, final String lastName) {
		peopleService.addPerson(email, firstName, lastName);
		return Response.ok().build();
	}

	@Override
	public Collection<Person> getPeople(final int page) {
		return peopleService.getPeople(page, 5);
	}

	@Override
	public Person getPerson(final String email) {
		return peopleService.getByEmail(email);
	}

	// @Override
	// public Response getPeople() {
	// StreamingOutput stream = new StreamingOutput() {
	// @Override
	// public void write(OutputStream os) throws IOException,
	// WebApplicationException {
	//
	// // create'n'configure
	// JsonGenerator jg = objectMapper.getFactory()
	// .createGenerator(os, JsonEncoding.UTF8);
	// // jg.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
	// // jg.setPrettyPrinter(new DefaultPrettyPrinter());
	//
	// // write
	// jg.writeStartArray();
	// for (Person person : peopleService.getPeople()) {
	// // jg.writeStartObject();
	// // jg.writeFieldName("id");
	// // jg.writeString(person.getKey().toString());
	// // jg.writeFieldName("name");
	// // jg.writeString(person.getValue());
	// // jg.writeEndObject();
	// jg.writeObject(person);
	// }
	// jg.writeEndArray();
	//
	// // flush'n'close
	// jg.flush();
	// jg.close();
	// }
	// };
	//
	// return Response.ok().entity(stream).type(MediaType.APPLICATION_JSON)
	// .build();
	// }

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
