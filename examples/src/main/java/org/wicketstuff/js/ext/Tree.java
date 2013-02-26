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
