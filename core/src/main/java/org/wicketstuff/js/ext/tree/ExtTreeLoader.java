package org.wicketstuff.js.ext.tree;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtObservable;
import org.wicketstuff.js.ext.data.AbstractExtBehavior;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtJsonRequestTarget;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;

@ExtClass("Ext.tree.TreeLoader")
public abstract class ExtTreeLoader extends ExtObservable {
    private static final long serialVersionUID = 1L;

    public static final String NODE_ID = "node";

    private AbstractAjaxBehavior behavior;

    @Override
    public void bind(Component component) {
        super.bind(component);
        behavior = new AbstractExtBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onRequest() {
                final RequestCycle requestCycle = RequestCycle.get();
                String nodeId = requestCycle.getRequest().getParameter(NODE_ID);
                if (nodeId != null) {
                    List<? extends ExtTreeNode> children = getChildren(nodeId);
                    JSONArray data = new JSONArray();
                    if (children != null) {
                        for (ExtTreeNode child : children) {
                            JSONObject object = new JSONObject();
                            ExtPropertyConverter.addProperties(child, child.getClass(), object);
                            data.put(object);
                        }
                    }
                    requestCycle.setRequestTarget(new ExtJsonRequestTarget(data));
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
        properties.put("dataUrl", behavior.getCallbackUrl());
        return properties;
    }

    protected abstract List<? extends ExtTreeNode> getChildren(String nodeId);

}
