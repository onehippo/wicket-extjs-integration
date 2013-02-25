package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.Panel")
public class ExtPanel extends ExtContainer {

    private ItemsRepeater buttons;

    @ExtProperty
    protected Boolean animCollapse;
    @ExtProperty
    protected Boolean autoHeight;
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
    protected Boolean collapsible;
    @ExtProperty
    protected Boolean frame;
    @ExtProperty
    protected Boolean header;
    @ExtProperty
    protected Boolean split;
    @ExtProperty
    protected IModel<String> title;
    @ExtProperty
    protected Boolean titleCollapse;

    public ExtPanel() {
        this("item");
    }

    public ExtPanel(String id) {
        super(id);
        add(buttons = new ItemsRepeater("buttons"));
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (isRenderFromMarkup()) {
            get("items").add(new AttributeAppender("style", true, new Model<String>(bodyStyle), ";"));
        }
    }

    @Override
    protected final void onRender(MarkupStream markupStream) {
        super.onRender(markupStream);

        buttons.render(null);
    }

    @Override
    protected String getName() {
        return "x-panel";
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        super.onRenderProperties(properties);

        List<ExtButton> buttonsList = getButtons();
        if (buttonsList.size() > 0) {
            JSONArray jsonButtons = new JSONArray();
            for (ExtButton button : buttonsList) {
                jsonButtons.put(new JSONIdentifier(button.getMarkupId()));
            }
            setPropertyValue(properties, "buttons", jsonButtons);
        }
    }

    @Override
    public List<Component> getItems() {
        List<Component> itemsList = new LinkedList<Component>(super.getItems());
        Iterator<? extends ExtComponent> iterator = buttons.extIterator();  
        while (iterator.hasNext()) {
            itemsList.add(iterator.next());
        }
        return itemsList;
    }


    List<ExtButton> getButtons() {
        List<ExtButton> buttonsList = new ArrayList<ExtButton>();
        if (buttons != null) {
            Iterator<? extends Component> iterator = buttons.extIterator();
            while (iterator.hasNext()) {
                Component component = iterator.next();
                if (component instanceof ExtButton) {
                    buttonsList.add((ExtButton) component);
                } else {
                    assert (false);
                }
            }
        }
        return buttonsList;
    }

    @Override
    protected ExtEventAjaxBehavior newExtEventBehavior(final String event) {
        if ("expand".equals(event) || "close".equals(event) || "collapse".equals(event)
                || "activate".equals(event) || "deactivate".equals(event)) {
            return new ExtEventAjaxBehavior(null);
        }
        return super.newExtEventBehavior(event);
    }

    public void setAnimCollapse(Boolean animCollapse) {
        this.animCollapse = animCollapse;
    }

    public void setAutoHeight(Boolean autoHeight) {
        this.autoHeight = autoHeight;
    }

    public void addButton(ExtButton button) {
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

    public void setSplit(Boolean split) {
        this.split = split;
    }

    @ExtMethod
    public void setTitle(IModel<String> title) {
        if (title instanceof IComponentAssignedModel) {
            this.title = ((IComponentAssignedModel) title).wrapOnAssignment(this);
        } else {
            this.title = title;
        }
    }

    public IModel<String> getTitle() {
        return title;
    }

    public void setTitleCollapse(Boolean titleCollapse) {
        this.titleCollapse = titleCollapse;
    }


}
