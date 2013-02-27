package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.json.JSONArray;

import org.wicketstuff.js.ext.layout.DefaultLayout;
import org.wicketstuff.js.ext.layout.ILayout;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public abstract class ExtContainer extends ExtBoxComponent {

	private MarkupContainer itemsContainer;
	protected ILayout layout = new DefaultLayout();

	public ExtContainer(String id) {
		super(id);
	}

	protected boolean hasExtItems() {
		return getItemsContainer() == null || itemsContainer instanceof AbstractRepeater;
	}

	@Override
	protected void onBeforeRender() {
		if (!hasExtItems() && getItemsContainer() != null) {
			getItemsContainer().add(new SimpleAttributeModifier("class", getName() + "-body"));
		}

		// before render of parent first
		super.onBeforeRender();

		// then add dummy container
		if (getItemsContainer() == null) {
			addItemsContainer(new WebMarkupContainer("items"));
		}

	}

	public Component getItemsContainer() {
		return (itemsContainer != null) ? itemsContainer : get("items");
	}

	public void setItemsContainer(MarkupContainer itemsContainer) {
		this.itemsContainer = itemsContainer;
	}

	protected String getName() {
		return "x-container";
	}

	@Override
	protected boolean isRenderFromMarkup() {
		return super.isRenderFromMarkup() || !hasExtItems();
	}

	@Override
	protected void onRenderProperties() {
		JSONArray jsonItems = new JSONArray();
		for (ExtComponent item : getItems()) {
			jsonItems.put(new JSONIdentifier(item.getExtId()));
		}
		setIfNotNull("items", jsonItems);

		layout.applyLayout(this);
		super.onRenderProperties();
	}

	@Override
	public List<ExtComponent> getItems() {
		List<ExtComponent> itemsList = new ArrayList<ExtComponent>();
		if (itemsContainer != null) {
			Iterator<? extends Component> iterator = itemsContainer.iterator();
			while (iterator.hasNext()) {
				Component component = (Component) iterator.next();
				if (component instanceof ExtComponent) {
					itemsList.add((ExtComponent) component);
				} else {
					assert (false);
				}
			}
		}
		return itemsList;
	}

	public void addItem(ExtComponent... items) {
		for (ExtComponent item : items) {
			addItem((Component) item);
		}
	}

	public void addItem(Component item) {
		if (itemsContainer == null) {
			addItemsContainer(new ItemsRepeater("items"));
		}
		itemsContainer.add(item);
	}
	
	protected void addItemsContainer(MarkupContainer items) {
		setItemsContainer(items);
		this.add(items);
	}

	public void setLayout(ILayout layout) {
		this.layout = layout;
	}

	public final class ItemsRepeater extends AbstractRepeater {
		public ItemsRepeater(String id) {
			super(id);
		}

		@Override
		protected Iterator<? extends Component> renderIterator() {
			return itemsContainer.iterator();
		}

		@Override
		protected void onPopulate() {
			/* noop */
		}
	}

}
