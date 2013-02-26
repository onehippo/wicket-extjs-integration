package org.wicketstuff.js.ext.tree;

import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.tree.TreePanel")
public class ExtTreePanel extends ExtPanel {
    private static final long serialVersionUID = 1L;

    private ExtTreeNode root;
    private ExtTreeLoader loader;

    @ExtProperty
    private boolean rootVisible;

    public ExtTreePanel(ExtTreeNode root) {
        add(this.root = root);

        rootVisible = true;
    }

    public void setRootVisible(boolean rootVisible) {
        this.rootVisible = rootVisible;
    }

    public boolean isRootVisible() {
        return rootVisible;
    }

    @Override
    protected void preRenderExtHead(StringBuilder js) {
        root.onRenderExtHead(js);
        if (loader != null) {
            loader.onRenderExtHead(js);
        }
        super.preRenderExtHead(js);
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        super.onRenderProperties(properties);

        properties.put("root", new JSONIdentifier(root.getJsObjectId()));
        if (loader != null) {
            properties.put("loader", new JSONIdentifier(loader.getJsObjectId()));
        }
    }

    public void setLoader(ExtTreeLoader loader) {
        this.loader = loader;
    }

    public ExtTreeLoader getLoader() {
        return loader;
    }

}
