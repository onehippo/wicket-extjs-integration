/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.layout.AccordionLayout;
import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;


public class Accordion extends ExamplesPage {

    public Accordion() {
        ExtPanel item1 = new TextPanel();
        item1.setTitle(new Model<String>("Accordion Item 1"));
        item1.add(new Label("text", "<empty panel>"));
        item1.setCls("empty");

        ExtPanel item2 = new TextPanel();
        item2.setTitle(new Model<String>("Accordion Item 2"));
        item2.add(new Label("text", "<empty panel>"));
        item2.setCls("empty");

        ExtPanel item3 = new TextPanel();
        item3.setTitle(new Model<String>("Accordion Item 3"));
        item3.add(new Label("text", "<empty panel>"));
        item3.setCls("empty");

        ExtPanel item4 = new TextPanel();
        item4.setTitle(new Model<String>("Accordion Item 4"));
        item4.add(new Label("text", "<empty panel>"));
        item4.setCls("empty");

        ExtPanel item5 = new TextPanel();
        item5.setTitle(new Model<String>("Accordion Item 5"));
        item5.add(new Label("text", "<empty panel>"));
        item5.setCls("empty");

        ExtPanel accordion = new ExtPanel();
        accordion.setRegion(Region.WEST);
        accordion.setMargins(5, 0, 5, 5);
        accordion.setSplit(true);
        accordion.setWidth(210);
        accordion.setLayout(new AccordionLayout());
        accordion.addItem(item1, item2, item3, item4, item5);

        ExtPanel center = new TextPanel();
        center.setRegion(Region.CENTER);
        center.setMargins(5, 5, 5, 0);
        center.setCls("empty");
        center.setBodyStyle("background:#f1f1f1");
        center.add(new MultiLineLabel("text", "\n \n <empty center panel>"));

        ExtViewport viewport = new ExtViewport("viewport");
        viewport.setLayout(new BorderLayout());
        viewport.addItem(accordion, center);

        add(viewport);
    }

}
