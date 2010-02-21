package de.fj.wickx.layout;

import de.fj.wickx.ExtComponent;

public abstract class ContainerLayout implements ILayout {
	
	private LayoutType type;

	public ContainerLayout(LayoutType type) {
		this.type = type;
	}
	
	public LayoutType getType() {
		return type;
	}
	
	public void applyLayout(ExtComponent component) {
		component.setPropertyValue("layout", getType().toString().toLowerCase());
	}
	
}
