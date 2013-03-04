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
package org.wicketstuff.js.ext;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class ExtMessageBoxCallback extends AbstractDefaultAjaxBehavior {

    private static final String POST_PARAM_BTN = "btn";
    private static final String POST_PARAM_TEXT = "text";

    public ExtMessageBoxCallback() {

    }

    public abstract void onClick(AjaxRequestTarget target, String button, String text);

    @Override
    protected void respond(AjaxRequestTarget target) {
        String button = RequestCycle.get().getRequest().getParameter(POST_PARAM_BTN);
        String text = RequestCycle.get().getRequest().getParameter(POST_PARAM_TEXT);
        onClick(target, button, text);
    }

    @Override
    public String toString() {
        return "function(btn, text) {" + getCallbackScript() + ";}";
    }

    @Override
    protected CharSequence getCallbackScript() {
        String postBody = String.format("'%s='+btn+'&%s='+text", POST_PARAM_BTN, POST_PARAM_TEXT);
        return generateCallbackScript("wicketAjaxPost('" + getCallbackUrl(false) + "', " + postBody);
    }

    @Override
    protected CharSequence getPreconditionScript() {
        return "return true;";
    }

}
