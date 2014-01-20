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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxAttributeName;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
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
        this.parameters = new String[0];
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
        IRequestParameters parameterMap = RequestCycle.get().getRequest().getQueryParameters();
        Map<String, JSONArray> filtered = new HashMap<String, JSONArray>();
        for (String parameter : parameters) {
            List<StringValue> values = parameterMap.getParameterValues(parameter);
            if (values == null) {
                continue;
            }
            JSONArray jsonObjects = new JSONArray();
            try {
                for (StringValue stringValue : values) {
                    String value = stringValue.toString();
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

    private void handle(AjaxRequestTarget target, Map<String, JSONArray> parameters) {
        for (ExtEventListener listener : listeners) {
            listener.onEvent(target, parameters);
        }
    }

    /**
     * Encodes and adds the this.fireEvent method's parameters to the wickAjaxURL {@inheritDoc}
     */
    public final JSONIdentifier getEventScript() {
        CallbackParameter[] callbackParameters = new CallbackParameter[getParameters().length];
        int index = 0;
        for (String parameter : getParameters()) {
            final String stringifyNonStrings = "typeof " + parameter + " === 'string' ? " + parameter + " : JSON.stringify(" + parameter + ")";
            callbackParameters[index] = CallbackParameter.converted(parameter, stringifyNonStrings);
            index++;
        }
        return new JSONIdentifier(getCallbackFunction(callbackParameters));
    }

    /**
     * FIXME: code copy & pasted from wicket; the Component should not be rendered as attribute
     * as this will trigger a precondition check (whether an element with the markupId exists)
     *
     * Generates the body the {@linkplain #getCallbackFunction(CallbackParameter...) callback
     * function}. To embed this code directly into a piece of javascript, make sure any context
     * parameters are available as local variables, global variables or within the closure.
     *
     * @param extraParameters
     * @return The body of the {@linkplain #getCallbackFunction(CallbackParameter...) callback
     *         function}.
     */
    public CharSequence getCallbackFunctionBody(CallbackParameter... extraParameters)
    {
        AjaxRequestAttributes attributes = getAttributes();
        attributes.setEventNames();
        CharSequence attrsJson = renderAjaxAttributes(getComponent().getPage(), attributes);
        StringBuilder sb = new StringBuilder();
        sb.append("var attrs = ");
        sb.append(attrsJson);
        sb.append(";\n");
        sb.append("var params = {");
        boolean first = true;
        for (CallbackParameter curExtraParameter : extraParameters)
        {
            if (curExtraParameter.getAjaxParameterName() != null)
            {
                if (!first)
                    sb.append(',');
                else
                    first = false;
                sb.append('\'')
                        .append(curExtraParameter.getAjaxParameterName())
                        .append("': ")
                        .append(curExtraParameter.getAjaxParameterCode());
            }
        }
        sb.append("};\n");
        if (attributes.getExtraParameters().isEmpty())
            sb.append("attrs." + AjaxAttributeName.EXTRA_PARAMETERS + " = params;\n");
        else
            sb.append("attrs." + AjaxAttributeName.EXTRA_PARAMETERS + " = Wicket.merge(attrs." +
                    AjaxAttributeName.EXTRA_PARAMETERS + ", params);\n");
        sb.append("Wicket.Ajax.ajax(attrs);\n");
        return sb;
    }

    @Override
    protected CharSequence getPreconditionScript() {
        return "return true;";
    }

    public void addListener(final ExtEventListener listener) {
        listeners.add(listener);
    }
}