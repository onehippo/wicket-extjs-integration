package org.wicketstuff.js.ext.util;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

public class ExtExamplesResourcesBehaviour extends AbstractBehavior {

    @Override
    public void bind(Component component) {

        component.add(JavascriptPackageResource
                .getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.js"));
        component.add(CSSPackageResource.getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.css"));

    }

}
