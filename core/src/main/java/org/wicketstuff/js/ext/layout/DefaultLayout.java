package org.wicketstuff.js.ext.layout;

import org.wicketstuff.js.ext.ExtComponent;

public class DefaultLayout implements ILayout {

	@Override
	public void applyLayout(ExtComponent component) {
		
	}

    @Override
    public LayoutType getType() {
        return LayoutType.AUTO;
    }

}