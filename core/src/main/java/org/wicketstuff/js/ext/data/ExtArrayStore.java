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

import java.util.List;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;


@ExtClass("Ext.data.ArrayStore")
public class ExtArrayStore<T> extends ExtStore<T> {

    private final List<T> data;

    public ExtArrayStore(List<ExtDataField> fields, List<T> data) {
        super(fields);
        this.data = data;
    }

    @Override
    public void onRenderProperties(JSONObject properties) throws JSONException {
        super.onRenderProperties(properties);

        JSONArray jsonFields = new JSONArray();
        for (ExtDataField field : getFields()) {
            JSONObject jsonField = new JSONObject();
            ExtPropertyConverter.setIfNotNull(jsonField, "name", field.getName());
            String type = (field.getType() != null) ? field.getType().getSimpleName().toLowerCase() : null;
            ExtPropertyConverter.setIfNotNull(jsonField, "type", type);
            ExtPropertyConverter.setIfNotNull(jsonField, "dateFormat", field.getDateFormat());
            jsonFields.put(jsonField);
        }
        properties.put("fields", jsonFields);

        JSONArray jsonData = new JSONArray();
        for (T dataObject : data) {
            JSONArray jsonLine = new JSONArray();
            for (ExtDataField field : getFields()) {
                Object value = PropertyResolver.getValue(field.getName(), dataObject);
                jsonLine.put(value);
            }
            jsonData.put(jsonLine);
        }
        properties.put("data", jsonData);
    }

}
