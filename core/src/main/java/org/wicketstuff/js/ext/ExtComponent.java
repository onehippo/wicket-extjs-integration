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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtEventListener;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.ExtResourcesBehaviour;
import org.wicketstuff.js.ext.util.ExtThemeBehavior;

import static org.wicketstuff.js.ext.util.ExtPropertyConverter.convert;

@ExtClass("Ext.Component")
public abstract class ExtComponent extends Panel implements IExtObservable {

    protected transient JSONObject properties = new JSONObject();

    private static final int FLAG_ONRENDERPROPERTIES = 1;
    private static final List SUPPORTED_EVENTS = Arrays.asList("enable", "disable", "show", "hide");

    private transient int flags = 0;

    @ExtProperty
    String cls;
    @ExtProperty
    IModel<String> fieldLabel;
    @ExtProperty
    Boolean hidden;
    @ExtProperty
    String stateId;
    @ExtProperty
    Boolean stateful;
    @ExtProperty
    String contentEl;

    Map<String, ExtEventAjaxBehavior> eventHandlers = new HashMap<String, ExtEventAjaxBehavior>();

    public ExtComponent(String id) {
        super(id);
        add(new ExtResourcesBehaviour());
    }

    protected boolean isRenderFromMarkup() {
        return isExtRoot();
    }

    protected boolean isExtRoot() {
        final MarkupContainer parent = getParent();
        return !(parent instanceof ExtComponent)
                && !(parent instanceof ItemsRepeater.ExtItem)
                && !(parent instanceof Form && parent.getParent() instanceof ItemsRepeater.ExtItem);
    }

    @Override
    protected void onBeforeRender() {
        updateContentElement();

        renderProperties();

        if (isRenderFromMarkup()) {
            setOutputMarkupId(true);
        } else {
            setRenderBodyOnly(true);
        }

        addThemeBehavior();

        super.onBeforeRender();
    }

    private void updateContentElement() {
        final List<Component> children = new LinkedList<Component>();
        visitChildren(new IVisitor<Component>() {

            @Override
            public Object component(Component component) {
                if ((!(component instanceof ExtComponent)) && (!(component instanceof ItemsRepeater)) && (!(component instanceof ItemsRepeater.ExtItem))) {
                    children.add(component);
                }
                return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
            }

        });
        if (children.size() > 1) {
            throw new WicketRuntimeException("More than one child component added to ExtComponent");
        }
        if (children.size() == 1) {
            Component component = children.get(0);
            component.setOutputMarkupId(true);
            component.add(new SimpleAttributeModifier("class", "x-hidden"));
            this.contentEl = component.getMarkupId();
        }
        onAfterUpdateContentElement();
    }

    protected void onAfterUpdateContentElement() {
    }

    // iterate through child components and render properties
    private void renderProperties() {
        properties = new JSONObject();
        try {
            properties.put("disabled", !isEnabled());
            ExtPropertyConverter.addProperties(this, getClass(), properties);
            addListeners();
            properties.put("resources", ExtObservableHelper.renderResources(this, ExtComponent.class));

            onRenderProperties(properties);
            if ((flags & FLAG_ONRENDERPROPERTIES) == 0) {
                throw new RuntimeException("Class in hierarchy of " + getClass().getName() + " has overridden onRenderProperties, but did not call parent");
            }
            flags &= ~FLAG_ONRENDERPROPERTIES;
        } catch (JSONException ex) {
            throw new RuntimeException("Error rendering properties", ex);
        }
    }

    protected void onRenderProperties(JSONObject properties) throws JSONException {
        if (isRenderFromMarkup()) {
            properties.put("applyTo", convert(getMarkupId()));
        }
        if (stateId == null) {
            properties.put("id", convert(getMarkupId()));
        }
        flags |= FLAG_ONRENDERPROPERTIES;
    }

    @Override
    public void renderHead(HtmlHeaderContainer container) {
        super.renderHead(container);
        // find out if this is the root of a ext-component structure
        if (isExtRoot()) {
            IHeaderResponse headerResponse = container.getHeaderResponse();
            StringBuilder js = new StringBuilder();
            onRenderExtHead(js);
            headerResponse.renderOnDomReadyJavascript(js.toString());
        }
    }

    private void onRenderExtHead(StringBuilder js) {
        preRenderExtHead(js);
        for (ExtObservable item : getExtObservables()) {
            item.onRenderExtHead(js);
        }
        for (ExtComponent item : getExtComponents()) {
            item.onRenderExtHead(js);
        }
        buildInstantiationJs(js, getExtClass(), properties);
        postRenderExtHead(js);
    }

    protected void preRenderExtHead(StringBuilder js) {
    }

    protected void buildInstantiationJs(StringBuilder js, String extClass, JSONObject properties) {
        js.append(String.format("var %s = new %s(%s);\n", getExtId(), extClass, properties.toString()));
    }

    protected void postRenderExtHead(StringBuilder js) {
    }

    protected final String getExtClass() {
        return ExtObservableHelper.getExtClass(this, ExtComponent.class);
    }

    private void addThemeBehavior() {
        if (isExtRoot()) {
            Component component = this;
            boolean foundTheme = false;
            while (component != null) {
                for (IBehavior behavior : component.getBehaviors()) {
                    if (behavior instanceof ExtThemeBehavior) {
                        foundTheme = true;
                        break;
                    }
                }
                if (foundTheme) {
                    break;
                }
                component = component.getParent();
            }
            if (!foundTheme) {
                add(getThemeBehavior());
            }
        }
    }

    protected ExtThemeBehavior getThemeBehavior() {
        return new ExtThemeBehavior();
    }

    private void addListeners() throws JSONException {
        if (!eventHandlers.isEmpty()) {
            JSONObject jsonListeners = new JSONObject();
            for (Map.Entry<String, ExtEventAjaxBehavior> entry : eventHandlers.entrySet()) {
                ExtEventAjaxBehavior behavior = entry.getValue();
                if (!getBehaviors().contains(behavior)) {
                    add(behavior);
                }
                jsonListeners.put(entry.getKey(), behavior.getEventScript());
            }
            properties.put("listeners", jsonListeners);
        }
    }

    protected final String getExtId() {
        return getMarkupId().replace("-", "_");
    }

    /**
     * Factory method to implement event specific ajax behavior. Default supported events are 'enable', 'disable',
     * 'show', 'hide'.
     *
     * @param event name of the event
     * @return the behavior for the event
     */
    protected ExtEventAjaxBehavior newExtEventBehavior(String event) {
        if (SUPPORTED_EVENTS.contains(event)) {
            return new ExtEventAjaxBehavior(null);
        }
        return new ExtEventAjaxBehavior();
    }

    public List<ExtComponent> getExtComponents() {
        final List<ExtComponent> itemsList = new ArrayList<ExtComponent>();
        visitChildren(new IVisitor() {

            @Override
            public Object component(Component component) {
                if (component instanceof ExtComponent) {
                    itemsList.add((ExtComponent) component);
                    return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
                }
                return CONTINUE_TRAVERSAL;
            }

        });
        return itemsList;
    }

    protected List<ExtObservable> getExtObservables() {
        return getBehaviors(ExtObservable.class);
    }

    public void addEventListener(String event, ExtEventListener listener) {
        if (!eventHandlers.containsKey(event)) {
            ExtEventAjaxBehavior behavior = newExtEventBehavior(event);
            eventHandlers.put(event, behavior);
        }
        eventHandlers.get(event).addListener(listener);
    }

    /* ExtProperties setters */
    public ExtComponent setDisabled(Boolean disabled) {
        return (ExtComponent) setEnabled(!disabled);
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public void setStateful(Boolean stateful) {
        this.stateful = stateful;
    }

    public void setFieldLabel(IModel<String> fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

}
