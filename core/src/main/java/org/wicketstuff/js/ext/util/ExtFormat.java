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
package org.wicketstuff.js.ext.util;

public abstract class ExtFormat {

    public static final ExtFormat usMoney = new ExtFormat() {
        @Override
        public String get() {
            return "Ext.util.Format.usMoney";
        }
    };

    public static ExtFormat dateRenderer(final String format) {
        return new ExtFormat() {

            @Override
            public String get() {
                return "Ext.util.Format.dateRenderer('" + format + "')";
            }

        };
    }

    public abstract String get();

}
