package de.fj.wickx.data;

import java.util.List;

import org.json.JSONArray;



public class ExtArrayStore<T> extends ExtStore<T> {

	private List<ExtField> fields;

	public ExtArrayStore(List<ExtField> fields) {
		this.fields = fields;
	}
	
	public List<ExtField> getFields() {
		return fields;
	}
	
	@Override
	public void onRenderExtHead(StringBuilder js) {
		JSONArray jsonData = new JSONArray();
		for (T dataObject : data) {
			JSONArray jsonLine = new JSONArray();
			for (ExtField field : fields) {
				jsonLine.put(filter(dataObject, field.getName()));
			}
			jsonData.put(jsonLine);
		}
		js.append("\nvar data = " + jsonData.toString() + ";\n");
		// TODO generate store
		js.append("var store = " + " new Ext.data.ArrayStore({fields: [{name: 'company'},{name: 'price', type: 'float'},{name: 'change', type: 'float'},{name: 'pctChange', type: 'float'},{name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}]});\n");
		js.append("store.loadData(data);\n");
		
	}

}
