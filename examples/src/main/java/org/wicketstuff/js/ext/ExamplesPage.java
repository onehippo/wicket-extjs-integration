package org.wicketstuff.js.ext;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.js.ext.util.ExtResourcesBehaviour;

public class ExamplesPage extends WebPage {

    public ExamplesPage() {
        add(new ExtResourcesBehaviour());
        add(new ExtExamplesResourcesBehaviour());

    }

}
