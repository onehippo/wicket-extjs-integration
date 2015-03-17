/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.js.ext;

import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.util.ExtClass;
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

    public void addTab(Model<String> title, ExtComponent content) {
        if (content instanceof ExtPanel) {
            ((ExtPanel) content).setTitle(title);
            ((ExtPanel) content).setHeader(false);
            ((ExtPanel) content).setBorder(false);
            ((ExtPanel) content).setHidden(true);
        }
        addItem(content);
    }

    public void setDeferredRender(Boolean deferredRender) {
        this.deferredRender = deferredRender;
    }

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
