package org.wicketstuff.js.ext.data;

import org.wicketstuff.js.ext.ExtObservable;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.data.Node")
public class ExtNode extends ExtObservable {
    private static final long serialVersionUID = -4249679128427561861L;

    @ExtProperty
    private String id;

    @ExtProperty
    private boolean leaf;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return leaf;
    }

}
