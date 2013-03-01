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
package org.wicketstuff.js.ext.form;

import org.apache.wicket.model.IModel;
import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtClass("Ext.form.TimeField")
public class ExtTimeField<T> extends ExtComboBox<T> {

    @ExtProperty
    protected String minValue;
    @ExtProperty
    protected String maxValue;

    public ExtTimeField(String id) {
        this(id, null);
    }

    public ExtTimeField(final IModel<T> stringModel) {
        this("item", stringModel);
    }

    public ExtTimeField(String id, IModel<T> model) {
        super(id, model);
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

}
