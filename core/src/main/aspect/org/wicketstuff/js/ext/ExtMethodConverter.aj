package org.wicketstuff.js.ext;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

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
        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target != null) {
            String method = thisJoinPoint.getStaticPart().getSignature().getName();
            String args = generateArgs(thisJoinPoint.getArgs());
            String object;
            if (thisJoinPoint.getTarget() != null) {
                object = generateReferenceObject(thisJoinPoint.getTarget());
            } else {
                object = generateStaticPart(thisJoinPoint.getSignature().getDeclaringType());
            }
            String jsMethodCall = String.format("%s.%s(%s);", object, method, args);
            target.appendJavaScript(jsMethodCall);
        }
    }

}
