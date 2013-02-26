package org.wicketstuff.js.ext.util;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WicketEventReference;
import org.wicketstuff.js.ext.ExtBundle;
import org.wicketstuff.js.ext.adapter.wicket.ExtWicketAdapterBundle;

public class ExtResourcesBehaviour extends AbstractBehavior {

	@Override
	public void bind(Component component) {
		String extBase, extAll, patchedExtAjax, extAdapt;
		if (Application.get().getConfigurationType().equals(Application.DEVELOPMENT)) {
			extBase = ExtBundle.EXT_BASE_DEBUG;
			extAll = ExtBundle.EXT_ALL_DEBUG;
            patchedExtAjax = ExtWicketAdapterBundle.PATCHED_EXT_BASE_AJAX;
			extAdapt = ExtWicketAdapterBundle.EXT_WICKET_ADAPTER_DEBUG;
		} else {
			extBase = ExtBundle.EXT_BASE_DEPLOY;
			extAll = ExtBundle.EXT_ALL_DEPLOY;
            patchedExtAjax = ExtWicketAdapterBundle.PATCHED_EXT_BASE_AJAX;
            extAdapt = ExtWicketAdapterBundle.EXT_WICKET_ADAPTER_DEPLOY;
		}
		component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extBase));
		component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extAll));
        component.add(HeaderContributor.forJavaScript(WicketEventReference.INSTANCE));
        component.add(HeaderContributor.forJavaScript(WicketAjaxReference.INSTANCE));
        component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, patchedExtAjax));
        component.add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extAdapt));
	}

}
