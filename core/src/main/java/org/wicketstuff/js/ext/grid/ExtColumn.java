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
package org.wicketstuff.js.ext.grid;

import org.apache.wicket.model.Model;
import org.wicketstuff.js.ext.util.ExtColumnRenderer;
import org.wicketstuff.js.ext.util.ExtFormat;

public class ExtColumn {

    private String id;
    private Model<String> header;
    private Number width;
    private Boolean sortable;
    private String dataIndex;
    private ExtFormat renderer;
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

    public void setRenderer(ExtFormat renderer) {
        this.renderer = renderer;
    }

    public ExtFormat getRenderer() {
        return renderer;
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
