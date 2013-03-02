/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
