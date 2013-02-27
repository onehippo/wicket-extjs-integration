package org.wicketstuff.js.ext;

import org.wicketstuff.js.ext.util.ExtClass;

@ExtClass("Ext.Viewport")
public class ExtViewport extends ExtContainer {

	public ExtViewport(String id) {
		super(id);
	}
	
	@Override
	protected boolean isRenderFromMarkup() {
		return false;
	}

}
