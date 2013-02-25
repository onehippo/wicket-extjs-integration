package org.wicketstuff.js.ext;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.TabPanel")
public class ExtTabPanel extends ExtPanel {
	
	@ExtProperty
	Integer activeTab;
	@ExtProperty
	Boolean autoTabs = true;
	@ExtProperty
	Boolean deferredRender;
	@ExtProperty
	Boolean plain;

	public ExtTabPanel() {
        this("item");
    }

	public ExtTabPanel(String id) {
		super(id);
	}
	
	public void addTab(IModel<String> title, ExtComponent content) {
	    if (!"item".equals(content.getId())) {
	        throw new RuntimeException("content does not have id 'item'.");
	    }
		if (content instanceof ExtPanel) {
			((ExtPanel) content).setTitle(title);
			((ExtPanel) content).setHeader(false);
			((ExtPanel) content).setBorder(false);
			((ExtPanel) content).setHidden(true);
		}
		add(content);
	}

	public void setDeferredRender(Boolean deferredRender) {
		this.deferredRender = deferredRender;
	}
	
	@ExtMethod
	public void setActiveTab(Integer activeTab) {
		this.activeTab = activeTab;
	}
	
	public void setAutoTabs(Boolean autoTabs) {
		this.autoTabs = autoTabs;
	}
	
	public void setPlain(Boolean plain) {
		this.plain = plain;
	}

}
