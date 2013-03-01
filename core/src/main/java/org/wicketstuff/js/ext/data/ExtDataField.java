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


public class ExtDataField {

    private String name;
    private Class<?> type;
    private String dateFormat;


    public ExtDataField(String name) {
        this(name, null);
    }

    public ExtDataField(String name, Class<?> type) {
        this(name, type, null);
    }

    public ExtDataField(String name, Class<?> type, String dateFormat) {
        this.name = name;
        this.type = type;
        this.dateFormat = dateFormat;
    }


    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public String getDateFormat() {
        return dateFormat;
    }

}
