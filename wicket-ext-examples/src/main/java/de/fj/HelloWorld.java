package de.fj;

//Ext.getCmp('center-panel')

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;

import de.fj.wickx.ExtButton;
import de.fj.wickx.ExtPanel;
import de.fj.wickx.ExtTabPanel;
import de.fj.wickx.ExtWindow;
import de.fj.wickx.ExtWindow.CloseAction;
import de.fj.wickx.layout.FitLayout;

public class HelloWorld extends WebPage {

	public HelloWorld() {
		final ExtWindow win = new ExtWindow("win");
		win.setTitle(new Model<String>("Hello Dialog"));
		win.setLayout(new FitLayout());
		win.setWidth(500);
		win.setHeight(300);
		win.setCloseAction(CloseAction.HIDE);
		win.setPlain(true);

		final ExtTabPanel tabs = new ExtTabPanel("1");
		//tabs.setAutoTabs(true);
		tabs.setActiveTab(0);
		tabs.setDeferredRender(false);
		tabs.setBorder(false);
		
		ExtPanel tab1 = new ExtPanel("1");
		tab1.add(new MultiLineLabel("items", "Hello..."));
		
		ExtPanel tab2 = new ExtPanel("2");
		tab2.add(new MultiLineLabel("items", "... World!"));
		
		tabs.addTab(new Model<String>("Hello World 1"), tab1);
		tabs.addTab(new Model<String>("Hello World 2"), tab2);
		win.addItem(tabs);
		
		ExtButton extButton = new ExtButton("1", new Model<String>("Submit"));
		extButton.setDisabled(true);
		win.addButton(extButton);
		
		win.addButton(new ExtButton("2", new Model<String>("Close")) {
			@Override
			protected void onClick(AjaxRequestTarget target) {
				win.hide();
			}
		});
		add(win);

		add(new WebMarkupContainer("showButton").add(new AjaxEventBehavior("onclick") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				win.show();
			}
		}));
		
	}

}
