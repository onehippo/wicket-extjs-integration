package org.wicketstuff.js.ext.tree;

import org.wicketstuff.js.ext.data.ExtNode;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.tree.TreeNode")
public class ExtTreeNode extends ExtNode {
    private static final long serialVersionUID = 4738848287947196460L;

    @ExtProperty
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
}
