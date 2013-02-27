package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.json.JSONArray;

import org.wicketstuff.js.ext.util.ExtMethod;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public class ExtPanel extends ExtContainer {

	private MarkupContainer buttons;

	@ExtProperty
	protected Boolean animCollapse;
	@ExtProperty
	protected Boolean autoHeight;
	@ExtProperty
	protected String baseCls;
	@ExtProperty
	protected Boolean border;
	@ExtProperty
	protected String bodyStyle;
	@ExtProperty
	protected Boolean closable;
	@ExtProperty
	protected Boolean collapseFirst;
	@ExtProperty
	protected Boolean collapsed;
	@ExtProperty
	protected Number columnWidth;
	@ExtProperty
	protected Number colspan;
	@ExtProperty
	protected Boolean collapsible;
	@ExtProperty
	protected Boolean frame;
	@ExtProperty
	protected Boolean header;
	@ExtProperty
	protected String iconCls;
	@ExtProperty
	protected Boolean split;
	@ExtProperty
	protected IModel<String> title;
	@ExtProperty
	protected Boolean titleCollapse;

	public ExtPanel(String id) {
		super(id);
	}

	protected void addButtonsContainer(MarkupContainer buttons) {
		add(buttons);
	}

	@Override
	protected String getExtClass() {
		return "Ext.Panel";
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		if (buttons == null) {
			buttons = new ButtonsRepeater("buttons");
			addButtonsContainer(buttons);
		}
		if (isRenderFromMarkup()) {
			getItemsContainer().add(new AttributeAppender("style", true, new Model<String>(bodyStyle), ";"));
		}
	}
	
	@Override
	protected String getName() {
		return "x-panel";
	}

	@Override
	protected void onRenderProperties() {
		List<AbstractExtButton> buttonsList = getButtons();
		if (buttonsList.size() > 0) {
			JSONArray jsonButtons = new JSONArray();
			for (AbstractExtButton button : buttonsList) {
				jsonButtons.put(new JSONIdentifier(button.getMarkupId()));
			}
			setPropertyValue("buttons", jsonButtons);
		}
		super.onRenderProperties();
	}

	@Override
	public List<ExtComponent> getExtComponents() {
		List<ExtComponent> items = getItems();
		items.addAll(getButtons());
		return items;
	}

	List<AbstractExtButton> getButtons() {
		List<AbstractExtButton> buttonsList = new ArrayList<AbstractExtButton>();
		if (buttons != null) {
			Iterator<? extends Component> iterator = buttons.iterator();
			while (iterator.hasNext()) {
				Component component = (Component) iterator.next();
				if (component instanceof AbstractExtButton) {
					buttonsList.add((AbstractExtButton) component);
				} else {
					assert (false);
				}
			}
		}
		return buttonsList;
	}
	
	public void setAnimCollapse(Boolean animCollapse) {
		this.animCollapse = animCollapse;
	}
	
	public void setAutoHeight(Boolean autoHeight) {
		this.autoHeight = autoHeight;
	}
	
	public void setBaseCls(String baseCls) {
		this.baseCls = baseCls;
	}
	
	public void addButton(AbstractExtButton button) {
		if (buttons == null) {
			buttons = new ButtonsRepeater("buttons");
			addButtonsContainer(buttons);
		}
		buttons.add(button);
	}

	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	
	public void setClosable(Boolean closable) {
		this.closable = closable;
	}
	
	public void setCollapseFirst(Boolean collapseFirst) {
		this.collapseFirst = collapseFirst;
	}
	
	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}
	
	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}
	
	public void setBorder(Boolean border) {
		this.border = border;
	}
	
	public void setFrame(Boolean frame) {
		this.frame = frame;
	}
	
	public void setHeader(Boolean header) {
		this.header = header;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setSplit(Boolean split) {
		this.split = split;
	}

	@ExtMethod
	public void setTitle(IModel<String> title) {
		this.title = title;
	}
	
	public IModel<String> getTitle() {
		return title;
	}
	
	public void setTitleCollapse(Boolean titleCollapse) {
		this.titleCollapse = titleCollapse;
	}

	private final class ButtonsRepeater extends AbstractRepeater {
		private ButtonsRepeater(String id) {
			super(id);
		}

		@Override
		protected Iterator<? extends Component> renderIterator() {
			return buttons.iterator();
		}

		@Override
		protected void onPopulate() {
			/* noop */
		}
	}
	
	
	// helper properties for layouts
	public void setColumnWidth(Number columnWidth) {
		this.columnWidth = columnWidth;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	

}
