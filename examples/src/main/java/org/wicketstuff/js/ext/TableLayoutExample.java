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

import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.layout.TableLayout;

public class TableLayoutExample extends ExamplesPage {

    public TableLayoutExample() {

        ExtPanel panel = new ExtPanel("panel");
        panel.setMarkupId("main-panel");

        panel.setBaseCls("x-plain");
        TableLayout layout = new TableLayout();
        layout.setColumns(3);
        panel.setLayout(layout);

        panel.addItem(newItem("Item 1"));
        panel.addItem(newItem("Item 2"));
        panel.addItem(newItem("Item 3"));
        panel.addItem(newItem("Item 4", 410, 2));
        panel.addItem(newItem("Item 5"));
        panel.addItem(newItem("Item 6"));
        panel.addItem(newItem("Item 7", 410, 2));
        panel.addItem(newItem("Item 8"));

        add(panel);
    }

    private ExtPanel newItem(String title, int width, int colspan) {
        ExtPanel item = newItem(title);
        item.setWidth(width);
        item.setColspan(colspan);
        return item;
    }

    private ExtPanel newItem(String title) {
        ExtPanel item = new ExtPanel();
        item.setTitle(new Model<String>(title));
        item.setFrame(true);
        item.setWidth(200);
        item.setHeight(200);
        return item;
    }
}
