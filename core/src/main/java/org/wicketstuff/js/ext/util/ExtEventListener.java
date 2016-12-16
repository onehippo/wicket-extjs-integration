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
package org.wicketstuff.js.ext.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtEventListener implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ExtEventListener.class);

    /**
     * Callback method that is invoked for client-side events.
     *
     * @param target Ajax request target
     * @deprecated see {@link #onEvent(AjaxRequestTarget, java.util.Map)}
     */
    @Deprecated
    public void onEvent(AjaxRequestTarget target) {
    }

    /**
     * Callback method with parameters retrieved from the request.
     *
     * @param target Ajax request target
     * @param parameters event parameters
     */
    public void onEvent(AjaxRequestTarget target, Map<String, JSONArray> parameters) {
        onEvent(target);
    }

    /**
     * Extract a singular parameter value from an event's parameters.
     */
    protected static Optional<String> getParameter(final String name, final Map<String, JSONArray> parameters) {
        final JSONArray values = parameters.get(name);
        if (values != null && values.length() > 0) {
            try {
                return Optional.of(values.get(0).toString());
            } catch (JSONException e) {
                log.warn("Invalid JSON parameter '{}'", name, e);
            }
        }
        return Optional.empty();
    }
}
