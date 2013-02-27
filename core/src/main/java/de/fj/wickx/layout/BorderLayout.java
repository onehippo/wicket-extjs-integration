package de.fj.wickx.layout;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;

import de.fj.wickx.ExtComponent;

public class BorderLayout extends ContainerLayout {
	public BorderLayout() {
		super(LayoutType.BORDER);
	}

	public enum Region {
		NORTH, WEST, SOUTH, EAST, CENTER
	}

	@Override
	public void applyLayout(ExtComponent component) {
		for (ExtComponent item : component.getItems()) {
			item.add(new AttributeAppender("class", true, new Model<String>("x-border-panel"), " "));
		}
		super.applyLayout(component);
	}
}
