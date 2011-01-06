package de.fj.wickx.form;

import de.fj.wickx.ExtPanel;
import de.fj.wickx.util.ExtProperty;

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
