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
package org.wicketstuff.js.ext.data;

import java.util.List;

import org.apache.wicket.util.lang.PropertyResolver;
import org.wicketstuff.js.ext.grid.ExtColumn;

public abstract class ExtDataStore<T> {

    protected List<T> data;
    private ExtColumn[] columns;

    public void loadData(List<T> data) {
        this.data = data;
    }

    public abstract void onRenderExtHead(StringBuilder js);

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

    public void setColumns(ExtColumn[] columns) {
        this.columns = columns;
    }

}
