package org.wicketstuff.js.ext;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.Button")
public abstract class AbstractExtButton extends ExtBoxComponent {

	@ExtProperty
	protected IModel<String> text;

	public AbstractExtButton(String id, IModel<String> text) {
		super(id);
		this.text = text;
	}

	public void setText(IModel<String> text) {
		this.text = text;
	}

	protected void onClick(AjaxRequestTarget target) {

	}

}
