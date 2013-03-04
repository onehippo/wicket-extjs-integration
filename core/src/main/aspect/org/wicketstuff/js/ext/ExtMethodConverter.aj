package org.wicketstuff.js.ext;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import static org.wicketstuff.js.ext.util.ExtPropertyConverter.generateArgs;
import static org.wicketstuff.js.ext.util.ExtPropertyConverter.generateReferenceObject;
import static org.wicketstuff.js.ext.util.ExtPropertyConverter.generateStaticPart;

/**
 * @author frido
 *
 *         adds a javascript method call to request target if java a method
 *         annotated with \@ExtMethod has been called during an ajax request
 */
aspect ExtMethodConverter {

    public pointcut extMethod(): execution(@org.wicketstuff.js.ext.util.ExtMethod * *.*(..));

    after() returning : extMethod() {
        IRequestTarget requestTarget = RequestCycle.get().getRequestTarget();
        if (requestTarget instanceof AjaxRequestTarget) {
            AjaxRequestTarget ajaxRT = (AjaxRequestTarget) requestTarget;

            String method = thisJoinPoint.getStaticPart().getSignature().getName();
            String args = generateArgs(thisJoinPoint.getArgs());
            String object;
            if (thisJoinPoint.getTarget() != null) {
                object = generateReferenceObject(thisJoinPoint.getTarget());
            } else {
                object = generateStaticPart(thisJoinPoint.getSignature().getDeclaringType());
            }
            String jsMethodCall = String.format("%s.%s(%s);", object, method, args);
            ajaxRT.appendJavascript(jsMethodCall);
        }
    }

}
