package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;

@ExtClass("Ext.form.TriggerField")
public class ExtTriggerField<T> extends ExtTextField<T> {
	
	public ExtTriggerField(String id) {
		super(id);
	}
	
	public ExtTriggerField(String id, IModel<T> model) {
		super(id, model);
	}

}
