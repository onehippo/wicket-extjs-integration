package org.wicketstuff.js.ext.form;

import org.wicketstuff.js.ext.ExtBoxComponent;
import org.wicketstuff.js.ext.ExtEventAjaxBehavior;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.Field")
public abstract class ExtField extends ExtBoxComponent {
    @ExtProperty
    private Object value;

    @ExtProperty
    protected String name;

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    @Override
    protected ExtEventAjaxBehavior newExtEventBehavior(final String event) {
        if ("change".equals(event)) {
            return new ExtEventAjaxBehavior(null, "newValue", "oldValue");
        }
        return super.newExtEventBehavior(event);
    }

}
