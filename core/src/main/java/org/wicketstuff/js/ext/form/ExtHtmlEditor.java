package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;

public class ExtHtmlEditor<T> extends ExtField<T> {

	public ExtHtmlEditor(String id) {
		super(id);
	}

	public ExtHtmlEditor(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	protected String getExtClass() {
		return "Ext.form.HtmlEditor";
	}

}
