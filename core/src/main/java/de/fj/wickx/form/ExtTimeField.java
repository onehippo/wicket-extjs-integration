package de.fj.wickx.form;

import org.apache.wicket.model.IModel;

import de.fj.wickx.util.ExtProperty;

public class ExtTimeField<T> extends ExtComboBox<T> {
	
	@ExtProperty
	protected String minValue;
	@ExtProperty
	protected String maxValue;
	
	public ExtTimeField(String id, IModel<T> model) {
		super(id, model);
	}

	public ExtTimeField(String id) {
		super(id);
	}
	
	@Override
	protected String getExtClass() {
		return "Ext.form.TimeField";
	}
	
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

}
