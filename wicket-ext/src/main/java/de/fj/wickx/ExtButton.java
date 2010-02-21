package de.fj.wickx;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import de.fj.wickx.util.ExtProperty;
import de.fj.wickx.util.JSONIdentifier;

public class ExtButton extends ExtBoxComponent {
	
	@ExtProperty
	protected IModel<String> text;
	
	ExtButtonAjaxBehavior behaviour;

	public ExtButton(String id, IModel<String> text) {
		super(id);
		this.text = text;
		add(behaviour = new ExtButtonAjaxBehavior());
	}

	public ExtButton(String id) {
		this(id, null);
	}
		
	@Override
	public List<ExtComponent> getItems() {
		return Collections.emptyList();
	}

	@Override
	protected void onRenderProperties() {
		behaviour.onBeforeRenderExtHead();
		super.onRenderProperties();
	}

	@Override
	protected String getExtClass() {
		return "Ext.Button";
	}

	public void setText(IModel<String> text) {
		this.text = text;
	}

	protected void onClick(AjaxRequestTarget target) {

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
