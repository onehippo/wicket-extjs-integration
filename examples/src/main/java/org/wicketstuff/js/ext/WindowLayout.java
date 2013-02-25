package org.wicketstuff.js.ext;


import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.ExtTabPanel;
import org.wicketstuff.js.ext.ExtWindow;
import org.wicketstuff.js.ext.ExtWindow.CloseAction;
import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;


public class WindowLayout extends ExamplesPage {

	String bogusMarkup = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.\n\n"
			+ "Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.";

	public WindowLayout() {
        final ExtWindow win = new ExtWindow("win");
        win.setCloseAction(CloseAction.HIDE);
        win.setTitle(new Model<String>("Layout Window"));
        win.setClosable(true);
        win.setWidth(600);
        win.setHeight(350);
        win.setPlain(true);
        win.setLayout(new BorderLayout());
        
        ExtPanel nav = new ExtPanel();
        nav.setTitle(new Model<String>("Navigation"));
        nav.setRegion(Region.WEST);
        nav.setSplit(true);
        nav.setWidth(200);
        nav.setCollapsible(true);
        nav.setMargins(3, 0, 3, 3);
        nav.setCMargins(3, 3, 3, 3);
        win.add(nav);

		ExtTabPanel tabs = new ExtTabPanel();
		tabs.setRegion(Region.CENTER);
		tabs.setMargins(3, 3, 3, 0);
		tabs.setActiveTab(0);
		
		ExtPanel tab1 = new ExtPanel();
		tab1.add(new MultiLineLabel("bogus", bogusMarkup));
		tab1.setAutoScroll(true);
		tabs.addTab(new Model<String>("Bogus Tab"), tab1);
		
		ExtPanel tab2 = new ExtPanel();
		tab2.add(new MultiLineLabel("bogus2", bogusMarkup));
		tab2.setAutoScroll(true);
		tabs.addTab(new Model<String>("Another Tab"), tab2);

		ExtPanel closeableTab = new ExtPanel();
		closeableTab.add(new MultiLineLabel("bogus3", bogusMarkup));
		closeableTab.setClosable(true);
		closeableTab.setAutoScroll(true);
		tabs.addTab(new Model<String>("Closable Tab"), closeableTab);
		win.add(tabs);
		
		add(win);
		
		add(new WebMarkupContainer("showButton").add(new AjaxEventBehavior("onclick") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				win.show();
			}
		}));
	}

}
