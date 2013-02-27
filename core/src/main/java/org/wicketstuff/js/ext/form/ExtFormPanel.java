package org.wicketstuff.js.ext.form;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitListener;

import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtProperty;

public class ExtFormPanel<T> extends ExtPanel {
	
	public enum LabelAlign {LEFT, TOP, RIGHT}
	
	@ExtProperty
	protected LabelAlign labelAlign;
	@ExtProperty
	protected Number labelWidth;
	@ExtProperty
	protected String url;
	
	private Form<T> form;

	public ExtFormPanel(String id) {
		super(id);
		
		form = new Form<T>("form") {
			@Override
			protected void onSubmit() {
				System.out.println("form submitted");;
			}
		};
		form.setRenderBodyOnly(true);
		add(form);
	}
	
	@Override
	protected String getExtClass() {
		return "Ext.FormPanel";
	}
	
	@Override
	protected void addItemsContainer(MarkupContainer items) {
		setItemsContainer(items);
		form.add(items);
	}
	
	@Override
	protected void addButtonsContainer(MarkupContainer buttons) {
		form.add(buttons);
	}
	
	@Override
	protected void onRenderProperties() {
		if (form.getOutputMarkupId()) {
			setPropertyValue("formId", form.getMarkupId());
		}
		url = form.urlFor(IFormSubmitListener.INTERFACE).toString();
		super.onRenderProperties();
	}
	
	public void setLabelAlign(LabelAlign labelAlign) {
		this.labelAlign = labelAlign;
	}
	
	public void setLabelWidth(Number labelWidth) {
		this.labelWidth = labelWidth;
	}


}
