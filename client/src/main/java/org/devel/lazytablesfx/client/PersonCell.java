package org.devel.lazytablesfx.client;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import org.devel.jerseyfx.common.model.Person;

public class PersonCell extends ListCell<Person> {
	
	@Override
	protected void updateItem(Person item, boolean empty) {
		super.updateItem(item, empty);
		if(item != null)
			setGraphic(new Label(item.toString()));
    }
	
}
