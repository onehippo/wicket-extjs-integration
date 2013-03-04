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
package org.wicketstuff.js.ext.layout;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.ExtComponent;

public class BorderLayout extends ContainerLayout {
    public BorderLayout() {
        super(LayoutType.BORDER);
    }

    public enum Region {
        NORTH, WEST, SOUTH, EAST, CENTER
    }

    @Override
    public void applyLayout(ExtComponent component) {
        for (ExtComponent item : component.getExtComponents()) {
            item.add(new AttributeAppender("class", true, new Model<String>("x-border-panel"), " "));
        }
        super.applyLayout(component);
    }
}
