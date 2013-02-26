package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.WebPage;


public class ExamplesPage extends WebPage {

	public ExamplesPage() {

		add(new ExtExamplesResourcesBehaviour());
	}

}
