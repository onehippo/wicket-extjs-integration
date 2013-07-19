/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.js.ext;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
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

    private static final List SUPPORTED_EVENTS = Arrays.asList("expand", "close", "collapse", "activate", "deactivate");

    private final ItemsRepeater<AbstractExtButton> buttons = new ItemsRepeater<AbstractExtButton>("buttons");

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

    public ExtPanel() {
        this("item");
    }

    public ExtPanel(String id) {
        super(id);
        add(buttons);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (isRenderFromMarkup() && !hasBeenRendered()) {
            getItemsContainer().add(new AttributeAppender("style", true, new Model<String>(bodyStyle), ";"));
        }
    }

    @Override
    protected String getName() {
        return "x-panel";
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        List<AbstractExtButton> buttonsList = getButtons();
        if (buttonsList.size() > 0) {
            JSONArray jsonButtons = new JSONArray();
            for (AbstractExtButton button : buttonsList) {
                jsonButtons.put(new JSONIdentifier(button.getExtId()));
            }
            properties.put("buttons", jsonButtons);
        }
        super.onRenderProperties(properties);
    }

    @Override
    public List<ExtComponent> getExtComponents() {
        List<ExtComponent> items = getItems();
        items.addAll(getButtons());
        return items;
    }

    private List<AbstractExtButton> getButtons() {
        return buttons.getExtComponents();
    }

    protected final MarkupContainer getButtonsContainer() {
        return buttons;
    }

    @Override
    protected ExtEventAjaxBehavior newExtEventBehavior(final String event) {
        if (SUPPORTED_EVENTS.contains(event)) {
            return new ExtEventAjaxBehavior();
        }
        return super.newExtEventBehavior(event);
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

    // helper properties for layouts
    public void setColumnWidth(Number columnWidth) {
        this.columnWidth = columnWidth;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }


}
