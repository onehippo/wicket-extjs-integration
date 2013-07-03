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
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.JSONIdentifier;

public class ExtSubmitButton extends AbstractExtButton {

    public ExtSubmitButton() {
        this("item", null);
    }

    public ExtSubmitButton(String id) {
        this(id, null);
    }

    public ExtSubmitButton(final IModel<String> save) {
        this("item", save);
    }

    public ExtSubmitButton(String id, IModel<String> text) {
        super(id, text);
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        Form<?> form = findParent(Form.class);
        ExtAjaxFormSubmitBehaviour submitBehavior = new ExtAjaxFormSubmitBehaviour(form, "handler");
        add(submitBehavior);
        submitBehavior.addListener(properties);
        super.onRenderProperties(properties);
    }

    private final class ExtAjaxFormSubmitBehaviour extends AjaxFormSubmitBehavior {

        public ExtAjaxFormSubmitBehaviour(Form<?> form, String event) {
            super(form, event);
        }

        private void addListener(JSONObject properties) {
            try {
                properties.put(getEvent(), new JSONIdentifier(getCallback()));
            } catch (JSONException e) {
                throw new WicketRuntimeException(e);
            }
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
        protected void onSubmit(AjaxRequestTarget target) {

        }

        @Override
        protected void onError(AjaxRequestTarget target) {

        }
    }

}
