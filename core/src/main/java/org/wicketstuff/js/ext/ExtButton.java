package org.wicketstuff.js.ext;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.Button")
public class ExtButton extends ExtBoxComponent {
	
	@ExtProperty
	protected IModel<String> text;
	
	@ExtProperty
    private ExtComponent menu;

	ExtButtonAjaxBehavior behaviour;

	public ExtButton(IModel<String> text) {
	    this("item", text);
    }

    public ExtButton(String id, IModel<String> text) {
		super(id);
        if (text instanceof IComponentAssignedModel) {
            this.text = ((IComponentAssignedModel) text).wrapOnAssignment(this);
        } else {
		    this.text = text;
        }
		add(behaviour = new ExtButtonAjaxBehavior());
	}

	public ExtButton(String id) {
		this(id, null);
	}
	
	@Override
	protected void onRenderProperties(JSONObject properties) throws JSONException {
	    super.onRenderProperties(properties);
		behaviour.onBeforeRenderExtHead(properties);
	}

	public void setText(IModel<String> text) {
		this.text = text;
	}

	protected void onClick(AjaxRequestTarget target) {

	}

	public void setMenu(ExtComponent component) {
	    if (!"menu".equals(component.getId())) {
	        throw new RuntimeException("menu component provided that does not have id 'menu'");
	    }
        add(this.menu = component);
    }

    private final class ExtButtonAjaxBehavior extends AbstractDefaultAjaxBehavior {
		@Override
		protected void respond(AjaxRequestTarget target) {
			onClick(target);
		}

		private void onBeforeRenderExtHead(JSONObject properties) throws JSONException {
			properties.put("handler", new JSONIdentifier("function() {" + getCallbackScript() + ";}"));
		}
		
		@Override
		protected CharSequence getPreconditionScript() {
			return "return true;";
		}
	}

}
