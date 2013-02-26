package org.wicketstuff.js.ext;

import org.wicketstuff.js.ext.layout.BorderLayout.Region;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.BoxComponent")
public class ExtBoxComponent extends ExtComponent {
    private static final long serialVersionUID = 1L;

    @ExtProperty
    protected Boolean autoScroll;
    @ExtProperty
    protected Boolean autoWidth;
    @ExtProperty
    protected Number width;
    @ExtProperty
    protected Number height;
    @ExtProperty
    protected String margins;
    @ExtProperty
    protected String cmargins;
    @ExtProperty
    protected Region region;

    public ExtBoxComponent(String id) {
        super(id);
    }

    public ExtBoxComponent() {
        super("item");
    }

    public void setAutoScroll(Boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setAutoWidth(Boolean autoWidth) {
        this.autoWidth = autoWidth;
    }

    @ExtMethod
    public void setWidth(Number width) {
        this.width = width;
    }

    @ExtMethod
    public void setHeight(Number height) {
        this.height = height;
    }

    public void setMargins(Number top, Number right, Number bottom, Number left) {
        this.margins = String.format("%s %s %s %s", top, right, bottom, left);
    }

    public void setCMargins(Number top, Number right, Number bottom, Number left) {
        this.cmargins = String.format("%s %s %s %s", top, right, bottom, left);
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
