package de.fj.wickx.util;

import static de.fj.wickx.util.ExtPropertyConverter.generateArgs;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import de.fj.wickx.ExtComponent;

/**
 * @author frido
 * 
 *         adds a javascript method call to request target if java a method
 *         annotated with \@ExtMethod has been called during an ajax request
 */
aspect ExtMethodConverter {

	public pointcut extMethod() : execution(@ExtMethod * *.*());

	after() returning : extMethod() {
		IRequestTarget requestTarget = RequestCycle.get().getRequestTarget();
		if (requestTarget instanceof AjaxRequestTarget) {
			AjaxRequestTarget ajaxRT = (AjaxRequestTarget) requestTarget;
			ExtComponent target = (ExtComponent) thisJoinPoint.getTarget();
			String name = thisJoinPoint.getStaticPart().getSignature().getName();
			String args = generateArgs(thisJoinPoint.getArgs());
			String jsMethodCall = String.format("Ext.getCmp('%s').%s(%s);", target.getMarkupId(), name, args);
			ajaxRT.appendJavascript(jsMethodCall);
		}
	}

}
