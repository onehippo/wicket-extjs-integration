package de.fj.wickx.grid;

import org.apache.wicket.model.Model;

import de.fj.wickx.util.ExtColumnRenderer;
import de.fj.wickx.util.Renderer;

public class ExtColumn {

	private String id;
	private Model<String> header;
	private Number width;
	private Boolean sortable;
	private String dataIndex;
	private Renderer renderer;
	private ExtColumnRenderer columnRenderer;

	public void setId(String id) {
		this.id = id;
	}

	public void setHeader(Model<String> header) {
		this.header = header;
	}

	public void setWidth(Number width) {
		this.width = width;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getId() {
		return id;
	}

	public Model<String> getHeader() {
		return header;
	}

	public Number getWidth() {
		return width;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public String getRenderer() {
		if (renderer != null) {
			return renderer.toString();
		} else {
			return null;
		}
	}

	public void setColumnRenderer(ExtColumnRenderer columnRenderer) {
		this.columnRenderer = columnRenderer;
	}
	
	public ExtColumnRenderer getColumnRenderer() {
		return columnRenderer;
	}

	public void setRenderer(ExtColumnRenderer columnRenderer) {
		setColumnRenderer(columnRenderer);
	}

}
