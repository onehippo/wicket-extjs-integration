package de.fj;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;

import de.fj.wickx.ExtPanel;
import de.fj.wickx.ExtViewport;
import de.fj.wickx.layout.AccordionLayout;
import de.fj.wickx.layout.BorderLayout;
import de.fj.wickx.layout.BorderLayout.Region;


public class Accordion extends ExamplesPage {

	public Accordion() {
		ExtPanel item1 = new ExtPanel("1");
		item1.setTitle(new Model<String>("Accordion Item 1"));
		item1.add(new Label("items", "<empty panel>"));
		item1.setCls("empty");
		
		ExtPanel item2 = new ExtPanel("2");
		item2.setTitle(new Model<String>("Accordion Item 2"));
		item2.add(new Label("items", "<empty panel>"));
		item2.setCls("empty");
		
		ExtPanel item3 = new ExtPanel("3");
		item3.setTitle(new Model<String>("Accordion Item 3"));
		item3.add(new Label("items", "<empty panel>"));
		item3.setCls("empty");
		
		ExtPanel item4 = new ExtPanel("4");
		item4.setTitle(new Model<String>("Accordion Item 4"));
		item4.add(new Label("items", "<empty panel>"));
		item4.setCls("empty");
		
		ExtPanel item5 = new ExtPanel("5");
		item5.setTitle(new Model<String>("Accordion Item 5"));
		item5.add(new Label("items", "<empty panel>"));
		item5.setCls("empty");
		
		ExtPanel accordion = new ExtPanel("0");
		accordion.setRegion(Region.WEST);
		accordion.setMargins(5, 0, 5, 5);
		accordion.setSplit(true);
		accordion.setWidth(210);
		accordion.setLayout(new AccordionLayout());
		accordion.addItem(item1, item2, item3, item4, item5);
		
		ExtPanel center = new ExtPanel("1");
		center.setRegion(Region.CENTER);
		center.setMargins(5, 5, 5, 0);
		center.setCls("empty");
		center.setBodyStyle("background:#f1f1f1");
		center.add(new MultiLineLabel("items", "\n \n <empty center panel>"));
		
		ExtViewport viewport = new ExtViewport("viewport");
		viewport.setLayout(new BorderLayout());
		viewport.addItem(accordion, center);
		
		add(viewport);
	}

}
