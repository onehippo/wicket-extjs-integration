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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtObservable;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;

@ExtClass("Ext.data.Store")
public abstract class ExtStore<T> extends ExtObservable {
    private static final long serialVersionUID = 1L;

    private List<ExtDataField> fields;

    protected ExtStore(List<ExtDataField> fields) {
        this.fields = fields;
    }

    public List<ExtDataField> getFields() {
        return fields;
    }

    @Override
    protected JSONObject getProperties() throws JSONException {
        JSONObject properties = super.getProperties();
        JSONArray jsonFields = new JSONArray();
        for (ExtDataField field : fields) {
            JSONObject jsonField = new JSONObject();
            ExtPropertyConverter.setIfNotNull(jsonField, "name", field.getName());
            String type = (field.getType() != null) ? field.getType().getSimpleName().toLowerCase() : null;
            ExtPropertyConverter.setIfNotNull(jsonField, "type", type);
            ExtPropertyConverter.setIfNotNull(jsonField, "dateFormat", field.getDateFormat());
            jsonFields.put(jsonField);
        }
        properties.put("fields", jsonFields);
        return properties;
    }

}
