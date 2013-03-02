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
package org.wicketstuff.js.ext.util;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.wicketstuff.js.ext.ExtBundle;

/**
 * Adds the default non-styled ExtJs theme CSS to the header, and provides a hook method to add more CSS for a custom
 * theme.
 */
public abstract class ExtCustomThemeBehavior extends ExtThemeBehavior {

    @Override
    protected void onBind(Component component) {
        Component parent = component.getParent();
        boolean foundTheme = false;
        while (parent != null) {
            for (IBehavior behavior : parent.getBehaviors()) {
                if (behavior instanceof ExtThemeBehavior) {
                    foundTheme = true;
                    break;
                }
            }
            if (foundTheme) {
                break;
            }
            parent = parent.getParent();
        }
        if (!foundTheme) {
            component.add(CSSPackageResource.getHeaderContribution(ExtBundle.class, ExtBundle.EXT_ALL_NOTHEME_STYLE));
        }
        addCustomTheme(component);
    }

    /**
     * Adds custom theme CSS to a header.
     *
     * @param component the component to add CSS to.
     */
    protected abstract void addCustomTheme(Component component);

}
