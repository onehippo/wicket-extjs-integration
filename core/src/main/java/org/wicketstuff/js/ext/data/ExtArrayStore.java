package org.wicketstuff.js.ext.data;

import java.util.List;

import org.apache.wicket.util.lang.PropertyResolver;
import org.json.JSONArray;
import org.wicketstuff.js.ext.util.ExtClass;

@ExtClass("Ext.data.ArrayStore")
public class ExtArrayStore<T> extends ExtStore<T> {
    private static final long serialVersionUID = 1L;

    protected List<T> data;

    public ExtArrayStore(List<ExtField> fields) {
        super(fields);
    }

    public void loadData(List<T> data) {
        this.data = data;
    }

    @Override
    public void onRenderExtHead(StringBuilder js) {
        super.onRenderExtHead(js);

        JSONArray jsonData = new JSONArray();
        for (T dataObject : data) {
            JSONArray jsonLine = new JSONArray();
            for (ExtField field : getFields()) {
                Object value = PropertyResolver.getValue(field.getName(), dataObject);
                jsonLine.put(value);
            }
            jsonData.put(jsonLine);
        }
        js.append("\nvar data = " + jsonData.toString() + ";\n");
        js.append(getJsObjectId() + ".loadData(data);\n");
    }

}
