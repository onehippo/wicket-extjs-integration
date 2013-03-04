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
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

public class ExtExamplesResourcesBehaviour extends AbstractBehavior {

    @Override
    public void bind(Component component) {
        component.add(CSSPackageResource.getHeaderContribution(ExtBundle.class, ExtBundle.EXT_ALL_STYLE));

        component.add(JavascriptPackageResource
                .getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.js"));
        component.add(CSSPackageResource.getHeaderContribution(ExtExamplesResourcesBehaviour.class, "examples/examples.css"));

    }

}
