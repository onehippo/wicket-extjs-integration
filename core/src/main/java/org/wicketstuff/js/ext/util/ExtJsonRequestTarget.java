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

import org.apache.wicket.Application;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExtJsonRequestTarget implements IRequestHandler {

    private Object responseJson;

    public ExtJsonRequestTarget(JSONObject object) {
        this.responseJson = object;
    }

    public ExtJsonRequestTarget(JSONArray object) {
        this.responseJson = object;
    }

    @Override
    public void respond(IRequestCycle requestCycle) {
        WebResponse r = (WebResponse) requestCycle.getResponse();

        // Determine encoding
        final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
        r.setContentType("application/json;charset=" + encoding);

        // Make sure it is not cached
        r.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
        r.setHeader("Cache-Control", "no-cache, must-revalidate");
        r.setHeader("Pragma", "no-cache");

        r.write(responseJson.toString());
    }

    @Override
    public void detach(IRequestCycle requestCycle) {
    }

}