package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import org.wicketstuff.js.ext.util.ExtExamplesResourcesBehaviour;

public class ExamplesPage extends WebPage {

	public ExamplesPage() {

	}

	@Override
	protected void onBeforeRender() {
		add(new WebMarkupContainer("dummy").add(new ExtExamplesResourcesBehaviour()).setRenderBodyOnly(true));
		super.onBeforeRender();
	}

}
