package de.fj.wickx.form;

import org.apache.wicket.model.IModel;

import de.fj.wickx.util.ExtProperty;


public class ExtTextField<T> extends ExtField<T> {

	@ExtProperty
	protected Boolean allowBlank;
	@ExtProperty
	protected VTypes vtype;
	
	public ExtTextField(String id) {
		this(id, null);
	}
	
	public ExtTextField(String id, IModel<T> model) {
		super(id, model);
	}
	
	@Override
	protected String getExtClass() {
		return "Ext.form.TextField";
	};

	public void setAllowBlank(Boolean allowBlank) {
		this.allowBlank = allowBlank;
		field.setRequired(!allowBlank);
	}
	
	@Override
	public void setFieldLabel(IModel<String> fieldLabel) {
		super.setFieldLabel(fieldLabel);
		field.setLabel(fieldLabel);
	}

	public void setVtype(VTypes vtype) {
		this.vtype = vtype;
	}

}
