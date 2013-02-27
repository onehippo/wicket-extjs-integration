package org.wicketstuff.js.ext;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

public class ExtExamplesResourcesBehaviour extends AbstractBehavior {

    @Override
    public void bind(Component component) {
        component.add(CSSPackageResource.getHeaderContribution(ExtBundle.class, ExtBundle.EXT_ALL_STYLE));

        component.add(JavascriptPackageResource
                .getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.js"));
        component.add(CSSPackageResource.getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.css"));

    }

}
