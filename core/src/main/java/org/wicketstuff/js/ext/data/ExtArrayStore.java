package org.wicketstuff.js.ext.data;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;


@ExtClass("Ext.data.ArrayStore")
public class ExtArrayStore<T> extends ExtDataStore<T> {

    private List<ExtDataField> fields;

    public ExtArrayStore(List<ExtDataField> fields) {
        this.fields = fields;
    }

    public List<ExtDataField> getFields() {
        return fields;
    }

    @Override
    public void onRenderExtHead(StringBuilder js) {
        JSONArray jsonData = new JSONArray();
        for (T dataObject : data) {
            JSONArray jsonLine = new JSONArray();
            for (ExtDataField field : fields) {
                jsonLine.put(filter(dataObject, field.getName()));
            }
            jsonData.put(jsonLine);
        }
        js.append("\nvar data = " + jsonData.toString() + ";\n");

        JSONArray jsonFields = new JSONArray();
        for (ExtDataField field : fields) {
            JSONObject jsonField = new JSONObject();
            ExtPropertyConverter.setIfNotNull(jsonField, "name", field.getName());
            String type = (field.getType() != null) ? field.getType().getSimpleName().toLowerCase() : null;
            ExtPropertyConverter.setIfNotNull(jsonField, "type", type);
            ExtPropertyConverter.setIfNotNull(jsonField, "dateFormat", field.getDateFormat());
            jsonFields.put(jsonField);
        }

//		js.append("var store = " + " new Ext.data.ArrayStore({fields: " + jsonFields.toString() + "});\n");
        js.append("var store = " + " new Ext.data.ArrayStore({fields: [{name: 'company'},{name: 'price', type: 'float'},{name: 'change', type: 'float'},{name: 'pctChange', type: 'float'},{name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}]});\n");
        js.append("store.loadData(data);\n");

    }

}
