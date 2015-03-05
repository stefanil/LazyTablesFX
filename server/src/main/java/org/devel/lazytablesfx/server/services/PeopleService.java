package org.devel.lazytablesfx.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.devel.lazytablesfx.exceptions.PersonAlreadyExistsException;
import org.devel.lazytablesfx.exceptions.PersonNotFoundException;
import org.devel.lazytablesfx.model.Person;

public class PeopleService {

	private static PeopleService instance;

	public PeopleService() {
		for(int i=0; i<100000; i++) {
			addPerson("email_"+i, "sur_"+i, "last_"+i);
		}
	}

	public static PeopleService getInstance() {
		if (instance == null)
			instance = new PeopleService();
		return instance;
	}

	private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<String, Person>();
	
	public Collection<Person> getPeople() {
		return persons.values().stream().sorted().collect(Collectors.toList());
		
	}

	public Collection<Person> getPeople(int page, int pageSize) {
		
		final Collection<Person> slice = new ArrayList<Person>(pageSize);
		Iterator<String> iterator = persons.keySet().stream().sorted().iterator();
		for (int i = 0; slice.size() < pageSize && iterator.hasNext();) {
			if (++i > ((page - 1) * pageSize)) {
				slice.add(persons.get(iterator.next()));
			} else {
				iterator.next();
			}
		}

		return slice;
	}

	public Person getByEmail(final String email) {
		final Person person = persons.get(email);

		if (person == null) {
			throw new PersonNotFoundException(email);
		}

		return person;
	}

	public Person addPerson(final String email, final String firstName,
			final String lastName) {
		
		final Person person = new Person(email, firstName, lastName,
				new ArrayList<Person>() {
					private static final long serialVersionUID = 3018131847446402380L;
					{
						add(new Person("generic.mother@mail.com"));
					}
				});

		if (persons.putIfAbsent(email, person) != null) {
			throw new PersonAlreadyExistsException(email);
		}

		return person;
	}

	public void removePerson(final String email) {
		if (persons.remove(email) == null) {
			throw new PersonNotFoundException(email);
		}
	}

}
