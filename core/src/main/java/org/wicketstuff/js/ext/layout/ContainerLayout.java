package org.wicketstuff.js.ext.layout;

import org.json.JSONObject;

import org.wicketstuff.js.ext.ExtComponent;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;

public abstract class ContainerLayout implements ILayout {
	
	private LayoutType type;

	public ContainerLayout(LayoutType type) {
		this.type = type;
	}
	
	public LayoutType getType() {
		return type;
	}
	
	public void applyLayout(ExtComponent component) {
		JSONObject layoutConfig = new JSONObject();
		ExtPropertyConverter.addProperties(this, getClass(), layoutConfig);
		component.setPropertyValue("layoutConfig", layoutConfig);
		
		component.setPropertyValue("layout", getType().toString().toLowerCase());
	}
	
}
