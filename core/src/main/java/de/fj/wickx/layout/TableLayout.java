package de.fj.wickx.layout;

import de.fj.wickx.util.ExtProperty;

public class TableLayout extends ContainerLayout {
	
	@ExtProperty
	protected Number columns;
	
	public TableLayout() {
		super(LayoutType.TABLE);
	}
	
	public void setColumns(Number columns) {
		this.columns = columns;
	}

}
