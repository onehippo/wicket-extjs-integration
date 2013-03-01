package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;

@ExtClass("Ext.form.HtmlEditor")
public class ExtHtmlEditor<T> extends ExtField<T> {

    public ExtHtmlEditor(IModel<T> model) {
        super("item", model);
    }

    public ExtHtmlEditor(String id, IModel<T> model) {
        super(id, model);
    }

}
