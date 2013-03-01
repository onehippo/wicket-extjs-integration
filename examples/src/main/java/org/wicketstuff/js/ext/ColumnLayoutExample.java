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

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.layout.AccordionLayout;
import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;
import org.wicketstuff.js.ext.layout.ColumnLayout;

public class ColumnLayoutExample extends ExamplesPage {

    private static final String BOGUS_MARKUP = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.";

    public ColumnLayoutExample() {
        add(new ExtExamplesResourcesBehaviour());

        ExtPanel accordion = new ExtPanel();
        accordion.setRegion(Region.WEST);
        accordion.setTitle(new Model<String>("West"));
        accordion.setSplit(true);
        accordion.setWidth(200);
//		accordion.setMinSize(175);
//		accordion.setMaxSize(400);
        accordion.setCollapsible(true);
        accordion.setMargins(35, 0, 5, 5);
        accordion.setCMargins(35, 5, 5, 5);
        AccordionLayout layout = new AccordionLayout();
        layout.setAnimate(true);
        accordion.setLayout(layout);

        ExtPanel west1 = new TextPanel();
        west1.add(new MultiLineLabel("text", BOGUS_MARKUP));
        west1.setTitle(new Model<String>("Navigation"));
        west1.setAutoScroll(true);
        west1.setBorder(false);
        west1.setIconCls("nav");

        ExtPanel west2 = new TextPanel();
        west2.add(new MultiLineLabel("text", BOGUS_MARKUP));
        west2.setTitle(new Model<String>("Settings"));
        west2.setAutoScroll(true);
        west2.setBorder(false);
        west2.setIconCls("settings");

        accordion.addItem(west1, west2);

        ExtPanel center1 = new ExtPanel();
        center1.setColumnWidth(.33);
        center1.setBaseCls("x-plain");
        center1.setBodyStyle("padding:5px 0 5px 5px");
        center1.addItem(newBogusItem("A Panel"));

        ExtPanel center2 = new ExtPanel();
        center2.setColumnWidth(.33);
        center2.setBaseCls("x-plain");
        center2.setBodyStyle("padding:5px 0 5px 5px");
        center2.addItem(newBogusItem("A Panel"));

        ExtPanel center3 = new ExtPanel();
        center3.setColumnWidth(.33);
        center3.setBaseCls("x-plain");
        center3.setBodyStyle("padding:5px");
        center3.addItem(newBogusItem("A Panel"));
        center3.addItem(newBogusItem("Another Panel"));

        ExtPanel center = new ExtPanel();
        center.setRegion(Region.CENTER);
        center.setMargins(35, 5, 5, 0);
        center.setLayout(new ColumnLayout());
        center.setAutoScroll(true);
        center.addItem(center1, center2, center3);

        ExtViewport viewport = new ExtViewport("viewport");
        viewport.setLayout(new BorderLayout());
        viewport.addItem(accordion, center);

        add(viewport);
    }

    private ExtComponent newBogusItem(String title) {
        ExtPanel item = new TextPanel();
        item.setTitle(new Model<String>(title));
        item.add(new MultiLineLabel("text", BOGUS_MARKUP));
        return item;
    }

}
