package de.fj.wickx.util;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

import de.fj.ExtBundle;

public class ExtResourcesBehaviour extends AbstractBehavior {

	@Override
	public void bind(Component component) {
		component.add(CSSPackageResource.getHeaderContribution(ExtBundle.class, ExtBundle.EXT_ALL_STYLE));
		String extBase, extAll;
		if (Application.get().getConfigurationType().equals(Application.DEVELOPMENT)) {
			extBase = ExtBundle.EXT_BASE_DEBUG;
			extAll = ExtBundle.EXT_ALL_DEBUG;
		} else {
			extBase = ExtBundle.EXT_BASE_DEPLOY;
			extAll = ExtBundle.EXT_ALL_DEPLOY;
		}
		component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extBase));
		component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extAll));
		
	}

}
