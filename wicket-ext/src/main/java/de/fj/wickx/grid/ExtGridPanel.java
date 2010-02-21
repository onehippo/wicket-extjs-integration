package de.fj.wickx.grid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.fj.wickx.ExtPanel;
import de.fj.wickx.data.ExtStore;
import de.fj.wickx.util.ExtProperty;
import de.fj.wickx.util.ExtPropertyConverter;
import de.fj.wickx.util.JSONIdentifier;

public class ExtGridPanel<T> extends ExtPanel {

	@ExtProperty
	protected String autoExpandColumn;
	@ExtProperty
	protected Boolean stripeRows;
	
	private ExtStore<T> store;
	private ExtColumn[] columns;

	public ExtGridPanel(String id) {
		super(id);
	}

	@Override
	protected String getExtClass() {
		return "Ext.grid.GridPanel";
	}

	@Override
	protected void preRenderExtHead(StringBuilder js) {
		
		store.setColumns(columns);
		store.onRenderExtHead(js);
		
		try {
			properties.put("store", new JSONIdentifier("store"));

			JSONArray jsonColumns = new JSONArray();
			for (ExtColumn column : columns) {
				JSONObject jsonColumn = new JSONObject();
				ExtPropertyConverter.setIfNotNull(jsonColumn, "id", column.getId());
				ExtPropertyConverter.setIfNotNull(jsonColumn, "header", column.getHeader());
				ExtPropertyConverter.setIfNotNull(jsonColumn, "width", column.getWidth());
				ExtPropertyConverter.setIfNotNull(jsonColumn, "sortable", column.getSortable());
				ExtPropertyConverter.setIfNotNull(jsonColumn, "dataIndex", column.getDataIndex());
				ExtPropertyConverter.setIfNotNull(jsonColumn, "renderer", column.getRenderer());
				
				jsonColumns.put(jsonColumn);
			}
			
			properties.put("columns", jsonColumns);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.preRenderExtHead(js);
	}

	@Override
	protected void postRenderExtHead(StringBuilder js) {
		js.append(getMarkupId() + ".render('" + getMarkupId() + "');");
		super.postRenderExtHead(js);
	}

	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
	}

	public void setColumns(ExtColumn... columns) {
		this.columns = columns;
	}

	public void setStore(ExtStore<T> store) {
		this.store = store;
	}

	public void setStripeRows(Boolean stripeRows) {
		this.stripeRows = stripeRows;
	}

}
