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
