package org.wicketstuff.js.ext.form;

import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.FieldSet")
public class ExtFieldSet extends ExtPanel {

    @ExtProperty
    Boolean checkboxToggle;

    public ExtFieldSet() {
        this("item");
    }

    public ExtFieldSet(String id) {
        super(id);
    }

    public void setCheckboxToggle(Boolean checkboxToggle) {
        this.checkboxToggle = checkboxToggle;
    }

}
