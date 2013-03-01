package org.wicketstuff.js.ext.form;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.ExtBoxComponent;
import org.wicketstuff.js.ext.ExtEventAjaxBehavior;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.Field")
public class ExtField<T> extends ExtBoxComponent {

	@ExtProperty
	protected String name;
	@ExtProperty
	protected IModel<T> value;
	
	protected FormComponent<T> field;

	public ExtField(String id, IModel<T> model) {
		super(id);
		this.value = model;

		field = new TextField<T>("field", model);
        field.setRenderBodyOnly(true);
    }

    @Override
    protected void onAfterUpdateContentElement() {
        add(field);
        setName(field.getInputName());
        super.onAfterUpdateContentElement();
    }

    @Override
    protected ExtEventAjaxBehavior newExtEventBehavior(final String event) {
        if ("change".equals(event)) {
            return new ExtEventAjaxBehavior(null, "newValue", "oldValue");
        }
        return super.newExtEventBehavior(event);
    }

    public void setName(String name) {
		this.name = name;
	}


}
