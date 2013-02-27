package org.wicketstuff.js.ext.form;

import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtProperty;

public class ExtFieldSet extends ExtPanel {
	
	@ExtProperty
	Boolean checkboxToggle;
	
	public ExtFieldSet(String id) {
		super(id);
	}
	
	@Override
	protected String getExtClass() {
		return "Ext.form.FieldSet";
	}
	
	public void setCheckboxToggle(Boolean checkboxToggle) {
		this.checkboxToggle = checkboxToggle;
	}
	
}
