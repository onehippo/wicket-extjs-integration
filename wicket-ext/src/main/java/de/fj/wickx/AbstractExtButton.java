package de.fj.wickx;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import de.fj.wickx.util.ExtProperty;

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
	
	@Override
	protected String getExtClass() {
		return "Ext.Button";
	}

	@Override
	public List<ExtComponent> getItems() {
		return Collections.emptyList();
	}

	protected void onClick(AjaxRequestTarget target) {

	}

}
