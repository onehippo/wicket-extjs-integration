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

import org.wicketstuff.js.ext.layout.BorderLayout.Region;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.BoxComponent")
public class ExtBoxComponent extends ExtComponent {

    @ExtProperty
    protected String anchor;
    @ExtProperty
    protected Boolean autoScroll;
    @ExtProperty
    protected Boolean autoWidth;
    @ExtProperty
    protected Number width;
    @ExtProperty
    protected Number height;
    @ExtProperty
    protected String margins;
    @ExtProperty
    protected String cmargins;
    @ExtProperty
    protected Region region;

    public ExtBoxComponent() {
        this("item");
    }

    public ExtBoxComponent(String id) {
        super(id);
    }

    public void setAutoScroll(Boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setAutoWidth(Boolean autoWidth) {
        this.autoWidth = autoWidth;
    }

    public void setWidth(Number width) {
        this.width = width;
    }

    public void setHeight(Number height) {
        this.height = height;
    }

    public void setMargins(Number top, Number right, Number bottom, Number left) {
        this.margins = String.format("%s %s %s %s", top, right, bottom, left);
    }

    public void setCMargins(Number top, Number right, Number bottom, Number left) {
        this.cmargins = String.format("%s %s %s %s", top, right, bottom, left);
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }


}
