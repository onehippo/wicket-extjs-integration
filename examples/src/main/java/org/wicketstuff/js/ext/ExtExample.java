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

@ExtClass("Ext.example")
public class ExtExample {

    public static void showResult(String btn) {
        msg("Button Click", "You clicked the {0} button", btn, null);
    }

    public static void showResultText(String btn, String text) {
        msg("Button Click", "You clicked the {0} button and entered the text \"{1}\".", btn, text);
    }

    @ExtMethod
    public static void msg(String title, String msg, String btn, String text) {

    }


}
