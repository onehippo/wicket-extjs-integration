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
package org.wicketstuff.js.ext.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.FormPanel")
public class ExtFormPanel<T> extends ExtPanel {

    public enum LabelAlign {LEFT, TOP, RIGHT}

    @ExtProperty
    protected LabelAlign labelAlign;
    @ExtProperty
    protected Number labelWidth;
    @ExtProperty
    protected String url;

    private Form<T> form;

    public ExtFormPanel() {
        this("item");
    }

    public ExtFormPanel(String id) {
        super(id);

        form = new Form<T>("form") {
            @Override
            protected void onSubmit() {
                System.out.println("form submitted");
            }
        };
        form.setRenderBodyOnly(true);
    }

    @Override
    protected void onAfterUpdateContentElement() {
        add(form);
        form.add(getItemsContainer());
        form.add(getButtonsContainer());
        super.onAfterUpdateContentElement();
    }

    @Override
    protected void onRenderProperties(JSONObject properties) throws JSONException {
        if (form.getOutputMarkupId()) {
            properties.put("formId", form.getMarkupId());
        }
        url = form.urlFor(IFormSubmitListener.INTERFACE, null).toString();
        super.onRenderProperties(properties);
    }

    public void setLabelAlign(LabelAlign labelAlign) {
        this.labelAlign = labelAlign;
    }

    public void setLabelWidth(Number labelWidth) {
        this.labelWidth = labelWidth;
    }


}
