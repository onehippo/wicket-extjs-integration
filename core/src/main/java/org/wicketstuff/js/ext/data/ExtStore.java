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

    private List<ExtField> fields;

    protected ExtStore(List<ExtField> fields) {
        this.fields = fields;
    }

    public List<ExtField> getFields() {
        return fields;
    }

    @Override
    protected JSONObject getProperties() throws JSONException {
        JSONObject properties = super.getProperties();
        JSONArray jsonFields = new JSONArray();
        for (ExtField field : fields) {
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
