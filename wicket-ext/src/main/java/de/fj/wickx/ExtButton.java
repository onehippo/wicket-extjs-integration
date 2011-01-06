package de.fj.wickx;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import de.fj.wickx.util.JSONIdentifier;

public class ExtButton extends AbstractExtButton {

	private ExtButtonAjaxBehavior behaviour;

	public ExtButton(String id, IModel<String> text) {
		super(id, text);
		add(behaviour = new ExtButtonAjaxBehavior());
	}

	public ExtButton(String id) {
		this(id, null);
	}

	@Override
	protected void onRenderProperties() {
		behaviour.onBeforeRenderExtHead();
		super.onRenderProperties();
	}

	private final class ExtButtonAjaxBehavior extends AbstractDefaultAjaxBehavior {
		@Override
		protected void respond(AjaxRequestTarget target) {
			onClick(target);
		}

		private void onBeforeRenderExtHead() {
			setPropertyValue("handler", new JSONIdentifier("function() {" + getCallbackScript() + ";}"));
		}

		@Override
		protected CharSequence getPreconditionScript() {
			return "return true;";
		}
	}

}
