package org.wicketstuff.js.ext.layout;

import org.wicketstuff.js.ext.ExtComponent;

public abstract class ContainerLayout implements ILayout {
	
	private LayoutType type;

	public ContainerLayout(LayoutType type) {
		this.type = type;
	}
	
	public LayoutType getType() {
		return type;
	}
	
	public void applyLayout(ExtComponent component) {
	}
	
}
