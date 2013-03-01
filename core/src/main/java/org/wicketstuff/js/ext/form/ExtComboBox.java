package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;

@ExtClass("Ext.form.ComboBox")
public class ExtComboBox<T> extends ExtTriggerField<T> {

    public ExtComboBox(String id) {
        super(id);
    }

    public ExtComboBox(String id, IModel<T> model) {
        super(id, model);
    }

}
