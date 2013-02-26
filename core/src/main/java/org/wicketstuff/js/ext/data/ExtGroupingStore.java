package org.wicketstuff.js.ext.data;

import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

import java.util.List;

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


    public ExtGroupingStore(List<ExtField> fields) {
        super(fields);
    }

}
