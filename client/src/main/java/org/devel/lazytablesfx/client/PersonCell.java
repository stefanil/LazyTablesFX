package org.devel.lazytablesfx.client;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.devel.jerseyfx.common.model.Person;

public class PersonCell extends ListCell<PersonProxy> {

	private static final Logger LOGGER = Logger.getLogger(PersonCell.class);

	@Override
	protected void updateItem(PersonProxy person, boolean empty) {
		super.updateItem(person, empty);
		if (!empty && person != null) {
			if (person.isLoaded())
				updateItem(person.getPerson());
			else {
				setGraphic(new Label("Waiting ..."));
				person.load(Integer.valueOf(getIndex()),
						new Callback<Integer, Void>() {
							@Override
							public Void call(Integer index) {
								LOGGER.debug("Current Index: " + getIndex());
								LOGGER.debug("Original Index: " + index);
								updateItem(person.getPerson());
								return null;
							}
						});
			}
		}
	}

	private void updateItem(Person person) {
		setGraphic(new Label(person.toString()));
	}

}
