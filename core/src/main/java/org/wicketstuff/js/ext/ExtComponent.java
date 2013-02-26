package org.wicketstuff.js.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.resource.IPropertiesFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ItemsRepeater.ExtItem;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtEventListener;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.ExtResourcesBehaviour;
import org.wicketstuff.js.ext.util.ExtThemeBehavior;

import static org.wicketstuff.js.ext.util.ExtPropertyConverter.convert;

@ExtClass("Ext.Component")
public abstract class ExtComponent extends WebMarkupContainer implements IExtObservable {

    private transient JSONObject properties = new JSONObject();

    private static final int FLAG_ONRENDERPROPERTIES = 1;
    private transient int flags = 0;

    @ExtProperty
    String cls;
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

    protected boolean hasExtItems() {
        return false;
    }

    @Override
    protected void onBeforeRender() {
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
            this.contentEl = component.getMarkupId();
        }

        renderProperties();
        if (isRenderFromMarkup()) {
            setOutputMarkupId(true);
        } else {
            setRenderBodyOnly(true);
        }

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

        super.onBeforeRender();
    }

    protected ExtThemeBehavior getThemeBehavior() {
        return new ExtThemeBehavior();
    }

    @Override
    protected void onRender(MarkupStream markupStream) {
        if (isExtRoot()) {
            super.onRender(markupStream);
        }
    }

    @Override
    protected final void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);
    }

    @Override
    public void renderHead(HtmlHeaderContainer container) {
        super.renderHead(container);
        // find out if this is the root of a ext-component structure
        if (isExtRoot()) {
            IHeaderResponse headerResponse = container.getHeaderResponse();
            StringBuilder js = new StringBuilder();
            onRenderExtHead(js);
            headerResponse.renderOnDomReadyJavascript(" Ext.onReady(function() { " + js.toString() + " }); ");
        }
    }

    protected final boolean isExtRoot() {
        return !(getParent() instanceof ExtComponent) && !(getParent() instanceof ExtItem && getParent().getParent().getParent() instanceof ExtComponent);
    }

    private void renderProperties() {
        properties = new JSONObject();
        try {
            properties.put("disabled", !isEnabled());
            ExtPropertyConverter.addProperties(this, getClass(), properties);
            addListeners(properties);
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

    protected IPropertiesFactory getPropertiesFactory() {
        return Application.get().getResourceSettings().getPropertiesFactory();
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

    public void buildInstantiationJs(StringBuilder js, String extClass, JSONObject properties) {
        js.append(String.format("var %s = new %s(%s);\n", getMarkupId(), extClass, properties.toString()));
    }

    private String getExtClass() {
        return ExtObservableHelper.getExtClass(this, ExtComponent.class);
    }

    protected void postRenderExtHead(StringBuilder js) {

    }

    protected void preRenderExtHead(StringBuilder js) {

    }

    public static void setIfNotNull(JSONObject properties, String property, Object value) {
        if (value != null) {
            setPropertyValue(properties, property, value);
        }
    }

    public static void setPropertyValue(JSONObject properties, String name, Object value) {
        try {
            properties.put(name, convert(value));
        } catch (JSONException e) {
            throw new WicketRuntimeException(e);
        }
    }

    private void addListeners(JSONObject properties) throws JSONException {
        if (!eventHandlers.isEmpty()) {
            JSONObject jsonListeners = new JSONObject();
            for (Map.Entry<String, ExtEventAjaxBehavior> entry : eventHandlers.entrySet()) {
                ExtEventAjaxBehavior behavior = entry.getValue();
                if (!getBehaviors().contains(behavior)) {
                    add(behavior);
                }
                jsonListeners.put(entry.getKey(), behavior.getEventScript());
            }
            properties.put("listeners", convert(jsonListeners));
        }
    }

    /**
     * Factory method to implement event specific ajax behavior. Default supported events are 'enable', 'disable',
     * 'show', 'hide'.
     *
     * @param event name of the event
     * @return the behavior for the event
     */
    protected ExtEventAjaxBehavior newExtEventBehavior(String event) {
        if ("disable".equals(event) || "enable".equals(event) || "show".equals(event) || "hide".equals(event)) {
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

}
