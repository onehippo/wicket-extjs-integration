package org.wicketstuff.js.ext.data;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;

/**
 * Behavior base class that ensures that the requests sent from the client are recognised
 * by wicket as ajax requests.  These should be seen as ext-ajax, as opposed to wicket's
 * native wicket-ajax requests, based on {@link AbstractDefaultAjaxBehavior}.
 */
public abstract class AbstractExtBehavior extends AbstractAjaxBehavior {
    private static final long serialVersionUID = 1L;

    @Override
    public final CharSequence getCallbackUrl() {
        return super.getCallbackUrl();
    }

    @Override
    public CharSequence getCallbackUrl(boolean onlyTargetActivePage) {
        // FIXME: use request coding strategy instead of hardcoding the URL
        // This approach will fail when urls are encrypted or the component is used in a portal.
        return super.getCallbackUrl(onlyTargetActivePage) + "&wicket:ajax=true";
    }

}
