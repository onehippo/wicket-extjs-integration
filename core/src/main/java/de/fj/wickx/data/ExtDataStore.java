package de.fj.wickx.data;

import java.util.List;

import org.apache.wicket.util.lang.PropertyResolver;

import de.fj.wickx.grid.ExtColumn;

public abstract class ExtDataStore<T> {

	protected List<T> data;
	private ExtColumn[] columns;

	public void loadData(List<T> data) {
		this.data = data;
	}

	public abstract void onRenderExtHead(StringBuilder js);

	protected Object filter(T line, String property) {
		for (ExtColumn column : columns) {
			if (column.getDataIndex().equals(property)) {
				Object value = PropertyResolver.getValue(column.getDataIndex(), line);
				if (column.getColumnRenderer() != null) {
					return column.getColumnRenderer().getString(value, data.indexOf(line));
				} else {
					return value;
				}
			}
		}
		return null;
	}

	public void setColumns(ExtColumn[] columns) {
		this.columns = columns;
	}

}
