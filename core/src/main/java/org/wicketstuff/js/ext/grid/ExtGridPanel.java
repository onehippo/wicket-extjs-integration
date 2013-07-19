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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.ExtPanel;
import org.wicketstuff.js.ext.data.ExtStore;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;
import org.wicketstuff.js.ext.util.ExtPropertyConverter;
import org.wicketstuff.js.ext.util.JSONIdentifier;

@ExtClass("Ext.grid.GridPanel")
public class ExtGridPanel<T> extends ExtPanel {

    @ExtProperty
    protected String autoExpandColumn;
    @ExtProperty
    protected Boolean stripeRows;

    private ExtStore<T> store;
    private ExtColumn[] columns;

    public ExtGridPanel(String id) {
        super(id);
    }

    @Override
    protected void onBeforeRender() {
        if (!hasBeenRendered()) {
            add(store);
        }
        super.onBeforeRender();
    }

    @Override
    protected void preRenderExtHead(StringBuilder js) {
        try {
            properties.put("store", new JSONIdentifier(store.getJsObjectId()));

            JSONArray jsonColumns = new JSONArray();
            for (ExtColumn column : columns) {
                JSONObject jsonColumn = new JSONObject();
                ExtPropertyConverter.setIfNotNull(jsonColumn, "id", column.getId());
                ExtPropertyConverter.setIfNotNull(jsonColumn, "header", column.getHeader());
                ExtPropertyConverter.setIfNotNull(jsonColumn, "width", column.getWidth());
                ExtPropertyConverter.setIfNotNull(jsonColumn, "sortable", column.getSortable());
                ExtPropertyConverter.setIfNotNull(jsonColumn, "dataIndex", column.getDataIndex());
                ExtPropertyConverter.setIfNotNull(jsonColumn, "renderer", column.getRenderer());

                jsonColumns.put(jsonColumn);
            }

            properties.put("columns", jsonColumns);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.preRenderExtHead(js);
    }

    @Override
    protected void postRenderExtHead(StringBuilder js) {
        js.append(getMarkupId() + ".render('" + getMarkupId() + "');");
        super.postRenderExtHead(js);
    }

/*
    protected Object filter(T line, String property) {
        for (ExtColumn column : columns) {
            if (column.getDataIndex().equals(property)) {
                Object value = PropertyResolver.getValue(column.getDataIndex(), line);
                if (column.getColumnRenderer() != null) {
                    return column.getColumnRenderer().getString(value, data.indexOf(line));
                } else {
                    return value;
                }
            }
        }
        return null;
    }
*/

    public void setAutoExpandColumn(String autoExpandColumn) {
        this.autoExpandColumn = autoExpandColumn;
    }

    public void setColumns(ExtColumn... columns) {
        this.columns = columns;
    }

    public void setStore(ExtStore<T> store) {
        this.store = store;
    }

    public void setStripeRows(Boolean stripeRows) {
        this.stripeRows = stripeRows;
    }

}
