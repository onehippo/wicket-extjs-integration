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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public class ExtButton extends AbstractExtButton {

    private ExtButtonAjaxBehavior behaviour;

    public ExtButton() {
        this("item", null);
    }

    public ExtButton(IModel<String> text) {
        this("item", text);
    }

    public ExtButton(String id) {
        this(id, null);
    }

    public ExtButton(String id, IModel<String> text) {
        super(id, text);
        add(behaviour = new ExtButtonAjaxBehavior());
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        behaviour.onBeforeRenderExtHead(properties);
        super.onRenderProperties(properties);
    }

    private final class ExtButtonAjaxBehavior extends AbstractDefaultAjaxBehavior {
        @Override
        protected void respond(AjaxRequestTarget target) {
            onClick(target);
        }

        private void onBeforeRenderExtHead(final JSONObject properties) throws JSONException {
            properties.put("handler", new JSONIdentifier(getCallback()));
        }

        private CharSequence getCallback() {
            CharSequence ajaxAttributes = renderAjaxAttributes(getPage());
            return "function() {\n"+
                    "  var call = new Wicket.Ajax.Call(),\n"+
                    "     attributes = jQuery.extend({}, " + ajaxAttributes + ");\n"+
                    "  return call.ajax(attributes);\n"+
                    "}";
        }

        @Override
        public CharSequence getCallbackScript(Component component) {
            return "(function(){}())";
        }

        @Override
        protected CharSequence getPreconditionScript() {
            return "return true;";
        }
    }

}
