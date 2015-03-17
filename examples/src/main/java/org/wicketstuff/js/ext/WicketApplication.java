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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 */
public class WicketApplication extends WebApplication {
    /**
     * Constructor
     */
    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<? extends WebPage> getHomePage() {
        return ExamplesHome.class;
    }

    @Override
    protected void init() {
        mountPage("window/hello.html", HelloWorld.class);
        mountPage("window/layout.html", WindowLayout.class);
        mountPage("layout/accordion.html", Accordion.class);
        mountPage("tabs/tabs.html", Tabs.class);
        mountPage("menu/menu.html", MenuPage.class);
        mountPage("grid/array-grid.html", ArrayGrid.class);
        mountPage("layout/column.html", ColumnLayoutExample.class);
        mountPage("layout/table.html", TableLayoutExample.class);
        mountPage("form/dynamic.html", DynamicForms.class);
        mountPage("tree/tree.html", Tree.class);
        mountPage("form/form.html", Form.class);
        getMarkupSettings().setStripWicketTags(true);

        getResourceSettings().setResourcePollFrequency(null);
    }

}
