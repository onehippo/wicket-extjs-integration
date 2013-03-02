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

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.json.JSONArray;
import org.wicketstuff.js.ext.form.ExtFormPanel;
import org.wicketstuff.js.ext.form.ExtTextField;
import org.wicketstuff.js.ext.layout.BorderLayout;
import org.wicketstuff.js.ext.layout.BorderLayout.Region;
import org.wicketstuff.js.ext.util.ExtEventListener;

public class Form extends ExamplesPage {

    public Form() {

        ExtViewport viewport = new ExtViewport("viewport");
        viewport.setLayout(new BorderLayout());

        ExtFormPanel formPanel = new ExtFormPanel();
        formPanel.setRegion(Region.CENTER);
        formPanel.setMargins(5, 0, 5, 5);
        formPanel.setSplit(true);
        formPanel.setWidth(210);

        ExtTextField field = new ExtTextField(new Model<String>("testing 1 2 3"));
        field.addEventListener("change", new ExtEventListener() {
            @Override
            public void onEvent(final AjaxRequestTarget target, Map<String, JSONArray> parameters) {
                Object newValue = parameters.get("newValue");
                Object oldValue = parameters.get("oldValue");
                target.prependJavascript("alert('old: " + oldValue + ", new: " + newValue + "')");
            }
        });
        formPanel.add(field);

        viewport.add(formPanel);

        add(viewport);
    }

}
