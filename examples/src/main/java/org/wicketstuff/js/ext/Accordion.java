package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.ExtViewport;
import org.wicketstuff.js.ext.layout.AccordionLayout;
import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;

public class Accordion extends ExamplesPage {

    public Accordion() {

        ExtViewport viewport = new ExtViewport("viewport");
        viewport.setLayout(new BorderLayout());

        ExtPanel accordion = new ExtPanel();
        accordion.setRegion(Region.WEST);
        accordion.setMargins(5, 0, 5, 5);
        accordion.setSplit(true);
        accordion.setWidth(210);
        accordion.setLayout(new AccordionLayout());

        ExtPanel item1 = new ExtPanel();
        item1.setTitle(new Model<String>("Accordion Item 1"));
        item1.add(new Label("item1", "<empty panel>"));
        item1.setCls("empty");
        accordion.add(item1);

        ExtPanel item2 = new ExtPanel();
        item2.setTitle(new Model<String>("Accordion Item 2"));
        item2.add(new Label("item2", "<empty panel>"));
        item2.setCls("empty");
        accordion.add(item2);

        ExtPanel item3 = new ExtPanel();
        item3.setTitle(new Model<String>("Accordion Item 3"));
        item3.add(new Label("item3", "<empty panel>"));
        item3.setCls("empty");
        accordion.add(item3);

        ExtPanel item4 = new ExtPanel();
        item4.setTitle(new Model<String>("Accordion Item 4"));
        item4.add(new Label("item4", "<empty panel>"));
        item4.setCls("empty");
        accordion.add(item4);

        ExtPanel item5 = new ExtPanel();
        item5.setTitle(new Model<String>("Accordion Item 5"));
        item5.add(new Label("item5", "<empty panel>"));
        item5.setCls("empty");
        accordion.add(item5);

        viewport.add(accordion);

        ExtPanel center = new ExtPanel();
        center.setRegion(Region.CENTER);
        center.setMargins(5, 5, 5, 0);
        center.setCls("empty");
        center.setBodyStyle("background:#f1f1f1");
        center.add(new MultiLineLabel("item6", "\n \n <empty center panel>"));
        viewport.add(center);

        add(viewport);
    }

}
