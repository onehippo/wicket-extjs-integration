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

import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;

@ExtClass("Ext.MessageBox")
public class ExtMessageBox {

    public enum Buttons {
        OK, OKCANCEL, YESNOCANCEL
    }

    public enum Icons {
        ERROR, INFO, QUESTION, WARNING
    }

    @ExtMethod
    public static void show(ExtMessageBoxOptions options) {

    }

    @ExtMethod
    public static void confirm(String title, String msg, ExtMessageBoxCallback fn) {

    }

    @ExtMethod
    public static void prompt(String title, String msg, ExtMessageBoxCallback fn) {

    }

    @ExtMethod
    public static void alert(String title, String msg, ExtMessageBoxCallback fn) {

    }

    @ExtMethod
    public static void hide() {

    }

    @ExtMethod
    public static void updateProgress(Number value, String progressText, String msg) {

    }


}
