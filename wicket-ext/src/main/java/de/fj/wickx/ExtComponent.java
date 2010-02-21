package de.fj.wickx;

import static de.fj.wickx.util.ExtPropertyConverter.convert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.util.lang.PropertyResolver;
import org.json.JSONException;
import org.json.JSONObject;

import de.fj.ExtBundle;
import de.fj.wickx.util.ExtEventListener;
import de.fj.wickx.util.ExtProperty;
import de.fj.wickx.util.JSONIdentifier;

public abstract class ExtComponent extends Panel {

	protected transient JSONObject properties = new JSONObject();
	private String savedProperties;
	
	@ExtProperty
	String cls;
	@ExtProperty
	Boolean hidden;
	@ExtProperty
	String stateId;
	@ExtProperty
	Boolean stateful;
	
	Map<String, ExtEventListener> listeners = new HashMap<String, ExtEventListener>();
	
	public ExtComponent(String id) {
		super(id);

		add(CSSPackageResource.getHeaderContribution(ExtBundle.class, ExtBundle.EXT_ALL_STYLE));
		String extBase, extAll;
		if (Application.get().getConfigurationType().equals(Application.DEVELOPMENT)) {
			extBase = ExtBundle.EXT_BASE_DEBUG;
			extAll = ExtBundle.EXT_ALL_DEBUG;
		}
		else {
			extBase = ExtBundle.EXT_BASE_DEPLOY;
			extAll = ExtBundle.EXT_ALL_DEPLOY;
		}
		add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extBase));
		add(JavascriptPackageResource.getHeaderContribution(ExtBundle.class, extAll));
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
				&& !(getParent() instanceof AbstractRepeater && getParent().getParent() instanceof ExtComponent);
	}

	private void fireOnRenderProperties() {
		for (ExtComponent item : getExtComponents()) {
			item.fireOnRenderProperties();
		}
		onRenderProperties();
	}
	private void renderProperties() {
		for (ExtComponent item : getExtComponents()) {
			item.renderProperties();
		}
		
		setPropertyValue("disabled", !isEnabled());
		addProperties(getClass());
		addListeners();
	}

	private void handleExtHeader(StringBuilder js) {
		for (ExtComponent item : getExtComponents()) {
			item.handleExtHeader(js);
		}
		preRenderExtHead(js);
		js.append(String
				.format("var %s = new %s(%s);\n", getMarkupId(), getExtClass(), properties.toString()));
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
			setPropertyValue("id", getMarkupId());
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

	private void addProperties(Class<?> clazz) {
		Field fs[] = clazz.getDeclaredFields();
		for (Field f : fs) {
			if (f.isAnnotationPresent(ExtProperty.class)) {
				Object value = PropertyResolver.getValue(f.getName(), this);
				if (value != null) {
					setPropertyValue(f.getName(), value);
				}
			}
		}
		if (ExtComponent.class.isAssignableFrom(clazz.getSuperclass())) {
			addProperties(clazz.getSuperclass());
		}
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

	// serialization stuff
	private void writeObject(ObjectOutputStream out) throws IOException {
		savedProperties = properties.toString();
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		try {
			properties = new JSONObject(savedProperties);
		} catch (JSONException e) {
			throw new WicketRuntimeException(e);
		}
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
