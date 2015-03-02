package org.devel.lazytablesfx.client;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import org.apache.log4j.Logger;
import org.devel.lazytablesfx.model.Person;

public class PersonCell extends ListCell<PersonProxy> {

	private static final Logger LOGGER = Logger.getLogger(PersonCell.class);

	@Override
	protected void updateItem(PersonProxy person, boolean empty) {
		super.updateItem(person, empty);
		if (!empty && person != null) {
			if (person.isLoaded())
				updateItem(person.getSubject());
			else {
				setGraphic(new Label("Waiting ..."));
			}
		}
	}

	private void updateItem(Person person) {
		setGraphic(new Label(person.toString()));
	}

}
