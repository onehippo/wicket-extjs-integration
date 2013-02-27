package org.wicketstuff.js.ext.form;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import org.wicketstuff.js.ext.ExtBoxComponent;
import org.wicketstuff.js.ext.ExtComponent;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.Field")
public class ExtField<T> extends ExtBoxComponent {

	@ExtProperty
	protected String name;
	@ExtProperty
	protected IModel<T> value;
	
	protected TextField<T> field;
	
	public ExtField(String id) {
		this(id, null);
	}
	
	public ExtField(String id, IModel<T> model) {
		super(id);
		this.value = model;

		field = new TextField<T>("field", model);
		add(field);
		field.setRenderBodyOnly(true);
	}
	

	@Override
	protected void onRenderProperties() {
		setName(field.getInputName());
		super.onRenderProperties();
	}
	
	
	@Override
	public List<ExtComponent> getItems() {
		return Collections.emptyList();
	}

	public void setName(String name) {
		this.name = name;
	}

}
