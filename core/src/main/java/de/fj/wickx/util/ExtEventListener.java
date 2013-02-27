package de.fj.wickx.util;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface ExtEventListener extends Serializable {
	public void onEvent(AjaxRequestTarget target);
}
