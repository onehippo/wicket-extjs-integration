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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class ExtExamplesResourcesBehaviour extends Behavior {

    private static final CssResourceReference EXT_STYLESHEET = new CssResourceReference(ExtBundle.class, ExtBundle.EXT_ALL_STYLE);
    private static final CssResourceReference EXAMPLE_STYLESHEET = new CssResourceReference(ExtExamplesResourcesBehaviour.class, "examples/examples.css");
    private static final JavaScriptResourceReference EXAMPLE_EXTJS = new JavaScriptResourceReference(ExtExamplesResourcesBehaviour.class, "examples/examples.js");

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(EXT_STYLESHEET));

        response.render(JavaScriptHeaderItem.forReference(EXAMPLE_EXTJS));
        response.render(CssHeaderItem.forReference(EXAMPLE_STYLESHEET));

    }

}
