package org.wicketstuff.js.ext.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.FormPanel")
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
				System.out.println("form submitted");
			}
		};
		form.setRenderBodyOnly(true);
    }

    @Override
    protected void onAfterUpdateContentElement() {
        add(form);
        form.add(getItemsContainer());
        form.add(getButtonsContainer());
        super.onAfterUpdateContentElement();
    }

    @Override
	protected void onRenderProperties(JSONObject properties) throws JSONException {
		if (form.getOutputMarkupId()) {
			properties.put("formId", form.getMarkupId());
		}
		url = form.urlFor(IFormSubmitListener.INTERFACE).toString();
		super.onRenderProperties(properties);
	}
	
	public void setLabelAlign(LabelAlign labelAlign) {
		this.labelAlign = labelAlign;
	}
	
	public void setLabelWidth(Number labelWidth) {
		this.labelWidth = labelWidth;
	}


}
