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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtEventListener;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;

import static org.wicketstuff.js.ext.util.ExtPropertyConverter.convert;

@ExtClass("Ext.util.Observable")
public class ExtObservable extends AbstractBehavior implements IExtObservable {
    private static final long serialVersionUID = 1L;

    private String objectId = null;
    Component component = null;
    Map<String, ExtEventAjaxBehavior> eventHandlers = new HashMap<String, ExtEventAjaxBehavior>();
    private transient boolean arePropertiesRendered = false;
    private List<ExtObservable> observables = new LinkedList<ExtObservable>();
    private List<IHeaderContributor> contributors = new LinkedList<IHeaderContributor>();

    @Override
    public void bind(Component component) {
        this.component = component;
        for (Map.Entry<String, ExtEventAjaxBehavior> entry : eventHandlers.entrySet()) {
            ExtEventAjaxBehavior behavior = entry.getValue();
            if (!component.getBehaviors().contains(behavior)) {
                component.add(behavior);
            }
        }
    }

    @Override
    public void renderHead(IHeaderResponse headerResponse) {
        for (IHeaderContributor contributor : contributors) {
            contributor.renderHead(headerResponse);
        }
        for (ExtObservable item : getExtObservables()) {
            item.renderHead(headerResponse);
        }

        // find out if this is the root of a ext-component structure
        if (isExtRoot()) {
            StringBuilder js = new StringBuilder();
            onRenderExtHead(js);
            headerResponse.renderOnDomReadyJavascript(" Ext.onReady(function() { " + js.toString() + " }); ");
        }
    }

    protected final boolean isExtRoot() {
        return (component != null) && !(component instanceof ExtComponent);
    }

    public final String getJsObjectId() {
        if (objectId == null) {
            objectId = "object" + Session.get().nextSequenceValue();
        }
        return objectId;
    }

    /**
     * This method returns the properties in a transient JSONObject. Subclasses should add their properties when not
     * handled by the ExtProperty annotation.
     *
     * @return the properties of this observable
     */
    protected JSONObject getProperties() throws JSONException {
        JSONObject properties = new JSONObject();
        ExtPropertyConverter.addProperties(this, getClass(), properties);

        if (!eventHandlers.isEmpty()) {
            JSONObject jsonListeners = new JSONObject();
            for (Map.Entry<String, ExtEventAjaxBehavior> entry : eventHandlers.entrySet()) {
                ExtEventAjaxBehavior behavior = entry.getValue();
                jsonListeners.put(entry.getKey(), behavior.getEventScript());
            }
            properties.put("listeners", convert(jsonListeners));
        }
        renderProperties(properties);
        return properties;
    }

    private void renderProperties(final JSONObject properties) throws JSONException {
        arePropertiesRendered = false;
        properties.put("resources", ExtObservableHelper.renderResources(this, ExtObservable.class));
        onRenderProperties(properties);
        if (!arePropertiesRendered) {
            throw new RuntimeException("Class in hierarchy of " + getClass().getName() + " has overridden onRenderProperties, but did not call parent");
        }
    }

    @SuppressWarnings("unused")
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        arePropertiesRendered = true;
    }

    public void onRenderExtHead(StringBuilder js) {
        preRenderExtHead(js);
        try {
            JSONObject properties = getProperties();
            for (ExtObservable item : getExtObservables()) {
                item.onRenderExtHead(js);
            }
            buildInstantiationJs(js, getExtClass(), properties);
            postRenderExtHead(js);
        } catch (JSONException e) {
            throw new WicketRuntimeException("Properties JSON could not be built", e);
        }
    }

    public void buildInstantiationJs(StringBuilder js, String extClass, JSONObject properties) {
        js.append(String.format("var %s = new %s(%s);\n", getJsObjectId(), extClass, properties.toString()));
    }

    private String getExtClass() {
        return ExtObservableHelper.getExtClass(this, ExtObservable.class);
    }

    @SuppressWarnings("unused")
    protected void postRenderExtHead(StringBuilder js) {
    }

    @SuppressWarnings("unused")
    protected void preRenderExtHead(StringBuilder js) {
    }

    public ExtObservable add(IHeaderContributor contributor) {
        this.contributors.add(contributor);
        return this;
    }

    public ExtObservable add(ExtObservable observable) {
        this.observables.add(observable);
        return this;
    }

    protected List<ExtObservable> getExtObservables() {
        return this.observables;
    }

    public void addEventListener(String event, ExtEventListener listener) {
        if (!eventHandlers.containsKey(event)) {
            ExtEventAjaxBehavior behavior = newExtEventBehavior(event);
            eventHandlers.put(event, behavior);
        }
        eventHandlers.get(event).addListener(listener);
    }

    /**
     * Factory method to implement event specific ajax behavior. Default supported events are 'enable', 'disable',
     * 'show', 'hide'.
     *
     * @param event name of the event
     * @return behavior for the event
     */
    @SuppressWarnings("unused")
    protected ExtEventAjaxBehavior newExtEventBehavior(String event) {
        return new ExtEventAjaxBehavior();
    }

}
