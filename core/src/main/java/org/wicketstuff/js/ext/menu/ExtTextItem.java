package org.wicketstuff.js.ext.menu;

import org.wicketstuff.js.ext.ExtContainer;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.menu.TextItem")
public class ExtTextItem extends ExtContainer {
    private static final long serialVersionUID = 1L;

    @ExtProperty
    private String text;

    public ExtTextItem() {
        this("item");
    }

    public ExtTextItem(String id) {
        super(id);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
