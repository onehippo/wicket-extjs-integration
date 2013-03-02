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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtJsonRequestTarget;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.data.JsonStore")
public abstract class ExtJsonStore<T> extends ExtStore<T> {
    private static final long serialVersionUID = 1L;

    private static final String JSON_PROP_RECORDS = "records";
    private static final String JSON_PROP_TOTAL = "total";

    private final CreateAction CREATE_RECORDS = new CreateAction();
    private final UpdateAction UPDATE_RECORDS = new UpdateAction();

    @SuppressWarnings("unused")
    @ExtProperty
    private boolean autoDestroy = true;

    private AbstractAjaxBehavior behavior;

    public ExtJsonStore(List<ExtDataField> fields) {
        super(fields);

        behavior = new AbstractExtBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onRequest() {
                final RequestCycle requestCycle = RequestCycle.get();
                ServletWebRequest request = ((ServletWebRequest) requestCycle.getRequest());
                String xaction = request.getParameter("xaction");
                try {
                    IRequestTarget requestTarget;

                    if ("create".equals(xaction)) {
                        requestTarget = processRecords(request, CREATE_RECORDS, "Created record(s)");
                    } else if ("update".equals(xaction)) {
                        requestTarget = processRecords(request, UPDATE_RECORDS, "Updated record(s)");
                    } else {
                        if (!"read".equals(xaction)) {
                            throw new WicketRuntimeException("Unknown action " + xaction);
                        }
                        requestTarget = readRecords();
                    }
                    requestCycle.setRequestTarget(requestTarget);
                } catch (JSONException e) {
                    throw new WicketRuntimeException("JSON error while processing action '" + xaction + "'", e);
                }
            }

        };
    }

    private IRequestTarget readRecords() throws JSONException {
        long total = getTotal();
        JSONArray records = getData();

        JSONObject jsonData = new JSONObject();
        jsonData.put(JSON_PROP_RECORDS, records);
        if (total >= 0) {
            jsonData.put(JSON_PROP_TOTAL, total);
        }

        return new ExtJsonRequestTarget(jsonData);
    }

    private IRequestTarget processRecords(ServletWebRequest request, Action action, String successMsg)
            throws JSONException {
        JSONArray records = new JSONArray();
        String recordStr = request.getParameter(JSON_PROP_RECORDS);
        JSONTokener tokener = new JSONTokener(recordStr);
        try {
            if (recordStr.startsWith("[")) {
                JSONArray values = new JSONArray(tokener);
                for (int i = 0; i < values.length(); i++) {
                    JSONObject record = values.getJSONObject(i);
                    processRecord(action, record, records);
                }
            } else {
                JSONObject record = new JSONObject(tokener);
                processRecord(action, record, records);
            }
            return new ExtJsonRequestTarget(createResponse(true, successMsg));
        } catch (ActionFailedException e) {
            return new ExtJsonRequestTarget(createResponse(false, e.getMessage()));
        }
    }

    private void processRecord(final Action action, final JSONObject inputRecord, final JSONArray resultRecords)
            throws ActionFailedException, JSONException {
        JSONObject actionResultRecord = action.execute(inputRecord);
        if (actionResultRecord != null) {
            resultRecords.put(actionResultRecord);
        }
    }

    @Override
    public void bind(Component component) {
        super.bind(component);
        component.add(behavior);
    }

    @Override
    protected JSONObject getProperties() throws JSONException {
        JSONObject properties = super.getProperties();
        properties.put("url", behavior.getCallbackUrl());
        properties.put("root", JSON_PROP_RECORDS);
        properties.put("totalProperty", JSON_PROP_TOTAL);
        return properties;
    }

    /**
     * @return the array of records in this store.
     * @throws JSONException
     */
    protected abstract JSONArray getData() throws JSONException;

    /**
     * Returns the total number of records in this store, which is typically needed for pagination. When a negative
     * number is returned, the total number of records will not be set. This default implementation returns -1.
     *
     * @return the total number of records in this store, or a negative number if the total number of records in this
     * store is unknown or unneeded in the frontend.
     */
    protected long getTotal() {
        return -1;
    }

    /**
     * Creates a record in this store based on one of the input records.
     *
     * @param record the input data for creating the record
     * @return the created record, as a JSON object
     *
     * @throws ActionFailedException when creating the record failed. An message of this exception will be passed to
     * the {@link #createResponse(boolean, String)} method.
     * @throws JSONException when a JSON parsing error occurred
     */
    protected JSONObject createRecord(JSONObject record) throws ActionFailedException, JSONException {
        throw new JSONException("Creating records is not supported");
    }

    protected JSONObject updateRecord(JSONObject record) throws ActionFailedException, JSONException {
        throw new JSONException("Updating records is not supported");
    }

    /**
     * Creates the response for an action on this store. The given message is returned as the 'message' property.
     *
     * @param success whether the action was successful or not
     * @param message the message to include in the response
     * @return the reponse for a successful action
     *
     * @throws JSONException when creating the JSON response failed
     */
    protected JSONObject createResponse(boolean success, String message) throws JSONException {
        // first call the deprecated createResponse method for backwards compatibility
        JSONObject response = createResponse(success);
        if (response == null) {
            // the deprecated createReponse method has not been overridden; use the actual message
            response = new JSONObject();
            response.put("success", success);
            response.put("message", message);
        }
        return response;
    }

    /**
     * Creates the response for the current action.
     *
     * @param success whether or not the current action succeeded
     * @return the reponse for the current action
     *
     * @throws JSONException when creating the JSON response failed
     *
     * @deprecated Use {@link #createResponse(boolean, String)} instead.
     */
    @Deprecated
    protected JSONObject createResponse(boolean success) throws JSONException {
        return null;
    }


    /**
     * Internal command pattern for store actions.
     */
    private interface Action extends Serializable {
        public JSONObject execute(JSONObject record) throws ActionFailedException, JSONException;
    }

    private class CreateAction implements Action {
        @Override
        public JSONObject execute(JSONObject record) throws ActionFailedException, JSONException {
            return createRecord(record);
        }
    }

    private class UpdateAction implements Action {
        @Override
        public JSONObject execute(JSONObject record) throws ActionFailedException, JSONException {
            return updateRecord(record);
        }
    }

}
