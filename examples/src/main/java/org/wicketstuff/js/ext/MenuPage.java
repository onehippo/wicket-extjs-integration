package org.wicketstuff.js.ext;

//Ext.getCmp('center-panel')

import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.ExtButton;
import org.wicketstuff.js.ext.ExtToolbar;
import org.wicketstuff.js.ext.menu.ExtMenu;
import org.wicketstuff.js.ext.menu.ExtTextItem;


public class MenuPage extends ExamplesPage {

	public MenuPage() {
	    ExtToolbar toolbar = new ExtToolbar("toolbar");
	    ExtButton button = new ExtButton(new Model("click me"));

	    ExtMenu menu = new ExtMenu("menu");
	    ExtTextItem textItem = new ExtTextItem();
	    textItem.setText("I like Ext");
	    menu.add(textItem);

	    button.add(menu);

	    toolbar.add(button);
	    add(toolbar);
	}

}
