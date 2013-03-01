package org.wicketstuff.js.ext.layout;

import org.wicketstuff.js.ext.util.ExtProperty;

public class TableLayout extends ContainerLayout {

    @ExtProperty
    protected Number columns;

    public TableLayout() {
        super(LayoutType.TABLE);
    }

    public void setColumns(Number columns) {
        this.columns = columns;
    }

}
