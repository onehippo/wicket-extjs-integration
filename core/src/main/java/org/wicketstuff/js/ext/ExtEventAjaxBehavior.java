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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.js.ext.util.ExtEventListener;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public class ExtEventAjaxBehavior extends AbstractDefaultAjaxBehavior {

    static final Logger log = LoggerFactory.getLogger(ExtEventAjaxBehavior.class);

    private final List<ExtEventListener> listeners = new ArrayList<ExtEventListener>();
    private final String[] parameters;

    /**
     * Creates an ExtJS Ajax behavior for a callback function without any parameters.
     */
    public ExtEventAjaxBehavior() {
        this.parameters = null;
    }

    /**
     * Creates an ExtJS Ajax behavior for a callback function with the given parameter names.
     *
     * @param parameters the parameter names of the callback function of this behavior
     */
    public ExtEventAjaxBehavior(String... parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the parameter names of the callback function of this behavior
     */
    public String[] getParameters() {
        return parameters;
    }

    @Override
    protected final void respond(AjaxRequestTarget target) {
        String[] parameters = getParameters();
        if (parameters == null) {
            handle(target, Collections.<String, JSONArray>emptyMap());
        } else {
            Map<String, String[]> parameterMap = RequestCycle.get().getRequest().getParameterMap();
            Map<String, JSONArray> filtered = new HashMap<String, JSONArray>();
            for (String parameter : parameters) {
                String[] values = parameterMap.get(parameter);
                if (values == null) {
                    continue;
                }
                JSONArray jsonObjects = new JSONArray();
                try {
                    for (String value : values) {
                        if (value.startsWith("[")) {
                            JSONTokener tokener = new JSONTokener(value);
                            JSONArray subVals = new JSONArray(tokener);
                            for (int i = 0; i < subVals.length(); i++) {
                                jsonObjects.put(subVals.get(i));
                            }
                        } else if (value.startsWith("{")) {
                            JSONTokener tokener = new JSONTokener(value);
                            jsonObjects.put(new JSONObject(tokener));
                        } else {
                            jsonObjects.put(value);
                        }
                    }
                } catch (JSONException e) {
                    log.error("Could not parse request parameters", e);
                }
                filtered.put(parameter, jsonObjects);
            }
            handle(target, filtered);
        }
    }

    private void handle(AjaxRequestTarget target, Map<String, JSONArray> parameters) {
        for (ExtEventListener listener : listeners) {
            listener.onEvent(target, parameters);
        }
    }

    /**
     * Encodes and adds the this.fireEvent method's parameters to the wickAjaxURL {@inheritDoc}
     */
    @Override
    protected final CharSequence getCallbackScript(boolean onlyTargetActivePage) {
        StringBuilder b = new StringBuilder();
        b.append("wicketAjaxGet('");
        b.append(getCallbackUrl(onlyTargetActivePage));
        b.append("' + (Ext.urlEncode(args) != '' ? '&' + Ext.urlEncode(args) : '')");
        return generateCallbackScript(b.toString());
    }

    public final JSONIdentifier getEventScript() {
        String[] parameters = getParameters();
        if (parameters != null) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (int i = 0; i < parameters.length; i++) {
                String parameter = parameters[i];
                if (parameter == null) {
                    continue;
                }
                if (!first) {
                    sb.append(",\n");
                } else {
                    first = false;
                }
                sb.append(parameter);
                sb.append(':');
                sb.append("arguments[");
                sb.append(i);
                sb.append(']');
            }
            sb.append("}");
            return new JSONIdentifier("function() { var args=" + sb.toString() + ";" + getCallbackScript() + "}");
        } else {
            return new JSONIdentifier("function(args) { " + getCallbackScript() + "}");
        }
    }

    @Override
    protected CharSequence getPreconditionScript() {
        return "return true;";
    }

    public void addListener(final ExtEventListener listener) {
        listeners.add(listener);
    }
}