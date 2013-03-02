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
import java.util.List;

import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;
import org.wicketstuff.js.ext.tree.ExtAsyncTreeNode;
import org.wicketstuff.js.ext.tree.ExtTreeLoader;
import org.wicketstuff.js.ext.tree.ExtTreeNode;
import org.wicketstuff.js.ext.tree.ExtTreePanel;

public class Tree extends ExamplesPage {

    public Tree() {
        ExtViewport viewport = new ExtViewport("viewport");
        viewport.setLayout(new BorderLayout());

        ExtTreeNode root = new ExtAsyncTreeNode();
        root.setId("root");
        root.setText("root node");

        ExtTreeLoader loader = new ExtTreeLoader() {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<ExtTreeNode> getChildren(String nodeId) {
                if ("root".equals(nodeId)) {
                    List<ExtTreeNode> children = new ArrayList<ExtTreeNode>(2);
                    ExtTreeNode first = new ExtTreeNode();
                    first.setText("first");
                    children.add(first);
                    ExtTreeNode second = new ExtTreeNode();
                    second.setText("second");
                    children.add(second);
                    return children;
                }
                return Collections.emptyList();
            }

        };
        ExtTreePanel tree = new ExtTreePanel(root);
        tree.setRegion(Region.CENTER);
        tree.setLoader(loader);
        tree.add(loader);
        viewport.add(tree);

        add(viewport);
    }

}
