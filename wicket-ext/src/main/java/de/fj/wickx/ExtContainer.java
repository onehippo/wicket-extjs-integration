package de.fj.wickx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.json.JSONArray;

import de.fj.wickx.layout.DefaultLayout;
import de.fj.wickx.layout.ILayout;
import de.fj.wickx.util.JSONIdentifier;

public abstract class ExtContainer extends ExtBoxComponent {

	protected MarkupContainer items;
	protected ILayout layout = new DefaultLayout();

	public ExtContainer(String id) {
		super(id);
	}

	protected boolean hasExtItems() {
		return get("items") == null || get("items") instanceof AbstractRepeater;
	}

	@Override
	protected void onBeforeRender() {
		if (!hasExtItems()) {
			get("items").add(new SimpleAttributeModifier("class", getName() + "-body"));
		}
		if (hideLazyLoadContent()) {
			add(new AbstractBehavior() {
				@Override
				public void beforeRender(Component component) {
					component.getResponse().write("<div style=\"display:none\">");
				}
				@Override
				public void onRendered(Component component) {
					component.getResponse().write("</div>");
				}
			});
		}
		
		// before render of parent first
		super.onBeforeRender();
		
		// then add dummy container
		if (get("items") == null) {
			add(new WebMarkupContainer("items"));
		}
		
	}

	protected boolean hideLazyLoadContent() {
		return false;
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
			jsonItems.put(new JSONIdentifier(item.getMarkupId()));
		}
		setIfNotNull("items", jsonItems);

		layout.applyLayout(this);
		super.onRenderProperties();
	}

	@Override
	public List<ExtComponent> getItems() {
		List<ExtComponent> itemsList = new ArrayList<ExtComponent>();
		if (items != null) {
			Iterator<? extends Component> iterator = items.iterator();
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
		if (items == null) {
			add(items = new ItemsRepeater("items"));
		}
		items.add(item);
	}

	public void setLayout(ILayout layout) {
		this.layout = layout;
	}

	private final class ItemsRepeater extends AbstractRepeater {
		private ItemsRepeater(String id) {
			super(id);
		}

		@Override
		protected Iterator<? extends Component> renderIterator() {
			return items.iterator();
		}

		@Override
		protected void onPopulate() {
			/* noop */
		}
	}

}
