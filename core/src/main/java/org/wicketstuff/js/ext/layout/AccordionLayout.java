package org.wicketstuff.js.ext.layout;

import org.wicketstuff.js.ext.ExtComponent;
import org.wicketstuff.js.ext.ExtPanel;

public class AccordionLayout extends FitLayout {
	public AccordionLayout() {
		super(LayoutType.ACCORDION);
	}

	@Override
	public void applyLayout(ExtComponent component) {
		boolean isFirst = true;
		for (ExtComponent item : component.getExtComponents()) {
			if (item instanceof ExtPanel) {
				ExtPanel panel = (ExtPanel) item;
				panel.setAnimCollapse(false);
				panel.setCollapsible(true);
				panel.setAutoWidth(true);
				panel.setTitleCollapse(true);
				panel.setCollapseFirst(false);
				panel.setCollapsed(!isFirst);
			}
			isFirst = false;
		}
		
		super.applyLayout(component);
	}

}
