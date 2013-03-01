package org.wicketstuff.js.ext.util;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.json.JSONArray;

public abstract class ExtEventListener implements Serializable {

    /**
     * Callback method that is invoked for client-side events.
     *
     * @param target
     * @deprecated see {@link #onEvent(AjaxRequestTarget, java.util.Map)}
     */
    @Deprecated
    public void onEvent(AjaxRequestTarget target) {
    }

    /**
     * Callback method with parameters retrieved from the request.
     *
     * @param target
     * @param parameters
     */
    public void onEvent(AjaxRequestTarget target, Map<String, JSONArray> parameters) {
        onEvent(target);
    }
}
