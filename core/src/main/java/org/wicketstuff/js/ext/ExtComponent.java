package org.wicketstuff.js.ext;

import static org.wicketstuff.js.ext.util.ExtPropertyConverter.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;

import org.wicketstuff.js.ext.util.ExtEventListener;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.ExtResourcesBehaviour;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public abstract class ExtComponent extends Panel {

	protected transient JSONObject properties = null;

	@ExtProperty
	String cls;
	@ExtProperty
	IModel<String> fieldLabel;
	@ExtProperty
	Boolean hidden;
	@ExtProperty
	String stateId;
	@ExtProperty
	Boolean stateful;

	Map<String, ExtEventListener> listeners = new HashMap<String, ExtEventListener>();

	public ExtComponent(String id) {
		super(id);
		add(new ExtResourcesBehaviour());
	}

	protected boolean isRenderFromMarkup() {
		return isExtRoot();
	}

	@Override
	protected void onBeforeRender() {
		if (isExtRoot()) {
			fireOnRenderProperties();
			renderProperties();
		}
		if (isRenderFromMarkup()) {
			setOutputMarkupId(true);
		} else {
			setRenderBodyOnly(true);
		}
		super.onBeforeRender();
	}

	@Override
	public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);
		// find out if this is the root of a ext-component structure
		if (isExtRoot()) {
			IHeaderResponse headerResponse = container.getHeaderResponse();
			StringBuilder js = new StringBuilder();
			handleExtHeader(js);
			headerResponse.renderOnDomReadyJavascript(js.toString());
		}
	}

	protected boolean isExtRoot() {
		return !(getParent() instanceof ExtComponent)
				&& !(getParent() instanceof AbstractRepeater && getParent().getParent() instanceof ExtComponent)
				&& !(getParent() instanceof AbstractRepeater
						&& getParent().getParent() instanceof Form<?> && getParent().getParent().getParent() instanceof ExtComponent);
	}
	
	// iterate through child components and call onrenderproperties
	private void fireOnRenderProperties() {
		for (ExtComponent item : getExtComponents()) {
			item.fireOnRenderProperties();
		}
		properties = new JSONObject();
		onRenderProperties();
	}

	// iterate through child components and render properties
	private void renderProperties() {
		for (ExtComponent item : getExtComponents()) {
			item.renderProperties();
		}

		setPropertyValue("disabled", !isEnabled());
		ExtPropertyConverter.addProperties(this, getClass(), properties);
		addListeners();
	}

	private void handleExtHeader(StringBuilder js) {
		for (ExtComponent item : getExtComponents()) {
			item.handleExtHeader(js);
		}
		preRenderExtHead(js);
		js.append(String.format("var %s = new %s(%s);\n", getExtId(), getExtClass(), properties.toString()));
		properties = null;
		postRenderExtHead(js);
	}

	protected void postRenderExtHead(StringBuilder js) {

	}

	protected String getExtClass() {
		return "Ext.Component";
	}

	protected void preRenderExtHead(StringBuilder js) {

	}

	protected void setIfNotNull(String property, Object value) {
		if (value != null) {
			setPropertyValue(property, value);
		}
	}

	public void setPropertyValue(String name, Object value) {
		try {
			properties.put(name, convert(value));
		} catch (JSONException e) {
			throw new WicketRuntimeException(e);
		}
	}

	protected void onRenderProperties() {
		if (isRenderFromMarkup()) {
			setPropertyValue("applyTo", getMarkupId());
		}
		if (stateId == null) {
			setPropertyValue("id", getExtId());
		}
	}

	private void addListeners() {
		if (!listeners.isEmpty()) {
			JSONObject jsonListeners = new JSONObject();
			for (String event : listeners.keySet()) {
				ExtEventAjaxBehavior behavior = new ExtEventAjaxBehavior(event);
				add(behavior);
				behavior.addListener(jsonListeners);
			}
			setPropertyValue("listeners", jsonListeners);
		}
	}

	protected final String getExtId() {
		return getMarkupId().replace("-", "_");
	}

	public abstract List<ExtComponent> getItems();

	public List<ExtComponent> getExtComponents() {
		return getItems();
	}

	public void addEventListener(String event, ExtEventListener listener) {
		listeners.put(event, listener);
	}

	/* ExtProperties setters */
	public ExtComponent setDisabled(Boolean disabled) {
		return (ExtComponent) setEnabled(!disabled);
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public void setStateful(Boolean stateful) {
		this.stateful = stateful;
	}

	public void setFieldLabel(IModel<String> fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	private final class ExtEventAjaxBehavior extends AbstractDefaultAjaxBehavior {

		private String event;

		public ExtEventAjaxBehavior(String event) {
			this.event = event;
		}

		@Override
		protected void respond(AjaxRequestTarget target) {
			listeners.get(event).onEvent(target);
		}

		private ExtEventAjaxBehavior addListener(JSONObject jsonListeners) {
			try {
				jsonListeners.put(event, new JSONIdentifier("function() {" + getCallbackScript() + ";}"));
			} catch (JSONException e) {
				throw new WicketRuntimeException(e);
			}
			return this;
		}

		@Override
		protected CharSequence getPreconditionScript() {
			return "return true;";
		}
	}
}
