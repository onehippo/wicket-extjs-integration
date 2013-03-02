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

import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

/**
 * A specialized store implementation that provides for grouping records by one of the available fields.
 * This is usually used in conjunction with an Ext.grid.GroupingView to provide the data
 * model for a grouped GridPanel. Internally, GroupingStore is simply a normal
 * Store with multi sorting enabled from the start. The grouping field and direction
 * are always injected as the first sorter pair. GroupingView picks up on the
 * configured groupField and builds grid rows appropriately.
 */
@ExtClass("Ext.data.GroupingStore")
public abstract class ExtGroupingStore<T> extends ExtJsonStore<T> {

    private static final long serialVersionUID = 2722392293179394234L;
    /**
     * The direction to sort the groups. Defaults to 'ASC'.
     */
    @ExtProperty
    protected String groupDir = "ASC";

    /**
     * The field name by which to sort the store's data (defaults to '').
     */
    @ExtProperty
    protected String groupField = "";

    /**
     * True to sort the data on the grouping field when a grouping operation occurs,
     * false to sort based on the existing sort info (defaults to false).
     */
    @ExtProperty
    protected boolean groupOnSort = false;

    /**
     * True if the grouping should apply on the server side, false if it is local only (defaults to false).
     * If the grouping is local, it can be applied immediately to the data. If it is remote, then it will
     * simply act as a helper, automatically sending the grouping field name as the 'groupBy' param with each XHR call.
     */
    @ExtProperty
    protected boolean remoteSort = false;


    public ExtGroupingStore(List<ExtDataField> fields) {
        super(fields);
    }

}
