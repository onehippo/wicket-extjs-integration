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

import org.wicketstuff.js.ext.ExtComponent;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtProperty;

public class AccordionLayout extends FitLayout {

    @ExtProperty
    protected Boolean animate;

    public AccordionLayout() {
        super(LayoutType.ACCORDION);
    }

    @Override
    public void applyLayout(ExtComponent component) {
        boolean isFirst = true;
        for (ExtComponent item : component.getExtComponents()) {
            if (item instanceof ExtPanel) {
                ExtPanel panel = (ExtPanel) item;
                if (animate != null && animate == false) {
                    panel.setAnimCollapse(false);
                }
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

    public void setAnimate(Boolean animate) {
        this.animate = animate;
    }

}
