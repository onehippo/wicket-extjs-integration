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
package org.wicketstuff.js.ext.tree;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtObservable;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtJsonRequestTarget;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.tree.TreeLoader")
public abstract class ExtTreeLoader extends ExtObservable {
    private static final long serialVersionUID = 1L;

    public static final String NODE_ID = "node";

    private AbstractAjaxBehavior behavior;

    @Override
    public void bind(Component component) {
        super.bind(component);
        behavior = new AbstractAjaxBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onRequest() {
                final RequestCycle requestCycle = RequestCycle.get();
                StringValue nodeId = requestCycle.getRequest().getQueryParameters().getParameterValue(NODE_ID);
                if (nodeId != null) {
                    List<? extends ExtTreeNode> children = getChildren(nodeId.toString());
                    JSONArray data = new JSONArray();
                    if (children != null) {
                        for (ExtTreeNode child : children) {
                            JSONObject object = new JSONObject();
                            ExtPropertyConverter.addProperties(child, child.getClass(), object);
                            data.put(object);
                        }
                    }
                    requestCycle.scheduleRequestHandlerAfterCurrent(new ExtJsonRequestTarget(data));
                } else {
                    throw new WicketRuntimeException("No node id provided");
                }
            }
        };
        component.add(behavior);
    }

    @Override
    protected JSONObject getProperties() throws JSONException {
        JSONObject properties = super.getProperties();
        StringBuilder directFnSb = new StringBuilder();
        directFnSb.append("function(nodeId, callback) {");
        directFnSb.append("Ext.Ajax.request({ url: '");
        directFnSb.append(behavior.getCallbackUrl());
        directFnSb.append("', headers: { 'Wicket-Ajax': true }, ");
        directFnSb.append("params: { node: nodeId },\n");
        directFnSb.append("success: function(response, opts) {\n");
        directFnSb.append("    callback(Ext.decode(response.responseText), { status: true });\n");
        directFnSb.append("  },");
        directFnSb.append("failure: function() { callback(undefined, { status: false }); }");
        directFnSb.append("}");
        properties.put("directFn", new JSONIdentifier(directFnSb.toString()));
        return properties;
    }

    protected abstract List<? extends ExtTreeNode> getChildren(String nodeId);

}
