package org.wicketstuff.js.ext;

import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.ExtResizable;

@ExtClass("Ext.Window")
public class ExtWindow extends ExtPanel {

	public enum CloseAction {
		CLOSE, HIDE
	}

	@ExtProperty
	protected Boolean closeable;
	@ExtProperty
	protected CloseAction closeAction;
	@ExtProperty
	protected Boolean collapsed;
	@ExtProperty
	protected Boolean constrain;
	@ExtProperty
	protected Boolean constrainHeader;
	@ExtProperty
	protected Boolean draggable;
	@ExtProperty
	protected Boolean expandOnShow;
	// sets visibility on dom basis
	protected Boolean hidden;
	@ExtProperty
	protected Boolean initHidden;
	@ExtProperty
	protected Boolean maximizable;
	@ExtProperty
	protected Boolean maximized;
	@ExtProperty
	protected Number minHeight;
	@ExtProperty
	protected Number minWidth;
	@ExtProperty
	protected Boolean minimizable;
	@ExtProperty
	protected Boolean modal;
	@ExtProperty
	protected Boolean plain;
	@ExtProperty
	protected Boolean resizable;
	@ExtProperty
	protected ExtResizable resizeHandles;
	@ExtProperty
	protected Number x;
	@ExtProperty
	protected Number y;

	public ExtWindow(String id) {
		super(id);
	}

	@Override
	protected boolean isRenderFromMarkup() {
		return false;
	}
	
	@Override
	protected String getName() {
		return "x-window";
	}

	public void setCloseable(Boolean closeable) {
		this.closeable = closeable;
	}

	public void setCloseAction(CloseAction closeAction) {
		this.closeAction = closeAction;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	public void setConstrain(Boolean constrain) {
		this.constrain = constrain;
	}

	public void setConstrainHeader(Boolean constrainHeader) {
		this.constrainHeader = constrainHeader;
	}

	public void setDraggable(Boolean draggable) {
		this.draggable = draggable;
	}

	public void setExpandOnShow(Boolean expandOnShow) {
		this.expandOnShow = expandOnShow;
	}

	public void setHidden(Boolean hidden) {
		if (hidden) {
			hide();
		} else {
			show();
		}
	}

	public void setInitHidden(Boolean initHidden) {
		this.initHidden = initHidden;
	}

	public void setMaximizable(Boolean maximizable) {
		this.maximizable = maximizable;
	}

	public void setMaximized(Boolean maximized) {
		if (maximized) {
			maximize();
		} else {
			minimize();
		}
	}

	public void setMinHeight(Number minHeight) {
		this.minHeight = minHeight;
	}

	public void setMinWidth(Number minWidth) {
		this.minWidth = minWidth;
	}

	public void setMinimizable(Boolean minimizable) {
		this.minimizable = minimizable;
	}

	public void setModal(Boolean modal) {
		this.modal = modal;
	}

	public void setPlain(Boolean plain) {
		this.plain = plain;
	}

	public void setResizeable(Boolean resizeable) {
		this.resizable = resizeable;
	}

	public void setResizeHandles(ExtResizable resizeHandles) {
		this.resizeHandles = resizeHandles;
	}

	public void setX(Number x) {
		this.x = x;
	}

	public void setY(Number y) {
		this.y = y;
	}
	
	public void toggleMaximize() {
		if (maximized) {
			restore();
		}
		else {
			maximize();
		}
	}

	@ExtMethod
	public void center() {
	}

	@ExtMethod
	public void close() {
	}
	
	@ExtMethod
	public void focus() {
	}

	@ExtMethod
	public void hide() {
		hidden = true;
	}

	@ExtMethod
	public void maximize() {
		maximized = true;
	}

	@ExtMethod
	public void minimize() {
		maximized = false;
	}
	
	@ExtMethod
	public void restore() {
		maximized = false;
	}
	
	@ExtMethod
	public void setActive(Boolean active) {}
	
	@ExtMethod
	public void show() {
		hidden = false;
	}
	
	@ExtMethod
	public void toBack() {}
	
	@ExtMethod
	public void toFront() {}

}
