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
import org.wicketstuff.js.ext.menu.ExtMenu;
import org.wicketstuff.js.ext.menu.ExtTextItem;


public class MenuPage extends ExamplesPage {

    public MenuPage() {
        ExtToolbar toolbar = new ExtToolbar("toolbar");
        ExtButton button = new ExtButton(new Model("click me"));

        ExtMenu menu = new ExtMenu("menu");
        ExtTextItem textItem = new ExtTextItem();
        textItem.setText("I like Ext");
        menu.add(textItem);

        button.add(menu);

        toolbar.add(button);
        add(toolbar);
    }

}
