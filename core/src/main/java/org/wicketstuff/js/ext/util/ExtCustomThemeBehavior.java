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
