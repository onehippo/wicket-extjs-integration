package de.fj;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

import de.fj.wickx.ExtPanel;
import de.fj.wickx.layout.TableLayout;

public class TableLayoutExample extends WebPage {

	public TableLayoutExample() {
		
		ExtPanel panel = new ExtPanel("panel");
		panel.setMarkupId("main-panel");
		
		panel.setBaseCls("x-plain");
		TableLayout layout = new TableLayout();
		layout.setColumns(3);
		panel.setLayout(layout);
		
		panel.addItem(newItem("1", "Item 1"));
		panel.addItem(newItem("2", "Item 2"));
		panel.addItem(newItem("3", "Item 3"));
		panel.addItem(newItem("4", "Item 4", 410, 2));
		panel.addItem(newItem("5", "Item 5"));
		panel.addItem(newItem("6", "Item 6"));
		panel.addItem(newItem("7", "Item 7", 410, 2));
		panel.addItem(newItem("8", "Item 8"));
		
		add(panel);
	}

	private ExtPanel newItem(String id, String title, int width, int colspan) {
		ExtPanel item = newItem(id, title);
		item.setWidth(width);
		item.setColspan(colspan);
		return item;
	}

	private ExtPanel newItem(String id, String title) {
		ExtPanel item = new ExtPanel(id);
		item.setTitle(new Model<String>(title));
		item.setFrame(true);
		item.setWidth(200);
		item.setHeight(200);
		return item;
	}
}
