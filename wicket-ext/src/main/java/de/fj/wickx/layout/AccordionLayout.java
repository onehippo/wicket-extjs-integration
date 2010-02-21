package de.fj.wickx.layout;

import de.fj.wickx.ExtComponent;
import de.fj.wickx.ExtPanel;

public class AccordionLayout extends FitLayout {
	public AccordionLayout() {
		super(LayoutType.ACCORDION);
	}

	@Override
	public void applyLayout(ExtComponent component) {
		boolean isFirst = true;
		for (ExtComponent item : component.getItems()) {
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
