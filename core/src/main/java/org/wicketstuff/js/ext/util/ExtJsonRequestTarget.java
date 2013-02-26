package org.wicketstuff.js.ext.util;

import org.apache.wicket.Application;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExtJsonRequestTarget implements IRequestTarget {

    private Object responseJson;

    public ExtJsonRequestTarget(JSONObject object) {
        this.responseJson = object;
    }

    public ExtJsonRequestTarget(JSONArray object) {
        this.responseJson = object;
    }

    @Override
    public void respond(RequestCycle requestCycle) {
        WebResponse r = (WebResponse) requestCycle.getResponse();

        // Determine encoding
        final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
        r.setCharacterEncoding(encoding);
        r.setContentType("application/json;charset=" + encoding);

        // Make sure it is not cached
        r.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
        r.setHeader("Cache-Control", "no-cache, must-revalidate");
        r.setHeader("Pragma", "no-cache");

        r.write(responseJson.toString());
    }

    @Override
    public void detach(RequestCycle requestCycle) {
    }

}