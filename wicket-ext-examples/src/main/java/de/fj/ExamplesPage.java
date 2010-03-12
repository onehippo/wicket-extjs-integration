package de.fj;

import org.apache.wicket.markup.html.WebPage;

import de.fj.wickx.util.ExtExamplesResourcesBehaviour;

public class ExamplesPage extends WebPage {

	public ExamplesPage() {

		add(new ExtExamplesResourcesBehaviour());
	}

}
