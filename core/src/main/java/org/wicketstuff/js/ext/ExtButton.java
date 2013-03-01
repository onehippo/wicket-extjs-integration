package org.wicketstuff.js.ext;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public class ExtButton extends AbstractExtButton {

	private ExtButtonAjaxBehavior behaviour;

    public ExtButton() {
        this("item", null);
    }

    public ExtButton(IModel<String> text) {
        this("item", text);
    }

    public ExtButton(String id) {
        this(id, null);
    }

    public ExtButton(String id, IModel<String> text) {
		super(id, text);
		add(behaviour = new ExtButtonAjaxBehavior());
	}

	@Override
	protected void onRenderProperties(JSONObject properties) throws JSONException {
		behaviour.onBeforeRenderExtHead(properties);
		super.onRenderProperties(properties);
	}

	private final class ExtButtonAjaxBehavior extends AbstractDefaultAjaxBehavior {
		@Override
		protected void respond(AjaxRequestTarget target) {
			onClick(target);
		}

		private void onBeforeRenderExtHead(final JSONObject properties) throws JSONException {
			properties.put("handler", new JSONIdentifier("function() {" + getCallbackScript() + ";}"));
		}

		@Override
		protected CharSequence getPreconditionScript() {
			return "return true;";
		}
	}

}
