package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.TextField")
public class ExtTextField<T> extends ExtField<T> {

    @ExtProperty
    protected Boolean allowBlank;
    @ExtProperty
    protected VTypes vtype;

    public ExtTextField(String id) {
        this(id, null);
    }

    public ExtTextField(final IModel<T> stringModel) {
        this("item", stringModel);
    }

    public ExtTextField(String id, IModel<T> model) {
        super(id, model);
    }

    public void setAllowBlank(Boolean allowBlank) {
        this.allowBlank = allowBlank;
        field.setRequired(!allowBlank);
    }

    @Override
    public void setFieldLabel(IModel<String> fieldLabel) {
        super.setFieldLabel(fieldLabel);
        field.setLabel(fieldLabel);
    }

    public void setVtype(VTypes vtype) {
        this.vtype = vtype;
    }

}
