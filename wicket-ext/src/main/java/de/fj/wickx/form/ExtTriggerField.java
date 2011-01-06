package de.fj.wickx.form;

import org.apache.wicket.model.IModel;

public class ExtTriggerField<T> extends ExtTextField<T> {
	
	public ExtTriggerField(String id) {
		super(id);
	}
	
	public ExtTriggerField(String id, IModel<T> model) {
		super(id, model);
	}

}
