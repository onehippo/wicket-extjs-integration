package de.fj.wickx.util;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

import de.fj.ExtBundle;

public class ExtExamplesResourcesBehaviour extends AbstractBehavior {

	@Override
	public void bind(Component component) {

//		component.add(JavascriptPackageResource
//				.getHeaderContribution(ExtBundle.class, "examples/examples.js"));
//		component.add(CSSPackageResource.getHeaderContribution(ExtBundle.class, "examples/examples.css"));

	}

}
