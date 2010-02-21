package de.fj.wickx;



public class ExtViewport extends ExtContainer {

	public ExtViewport(String id) {
		super(id);
	}
	
	@Override
	protected boolean isRenderFromMarkup() {
		return false;
	}

	@Override
	protected String getExtClass() {
		return "Ext.Viewport";
	}

}
