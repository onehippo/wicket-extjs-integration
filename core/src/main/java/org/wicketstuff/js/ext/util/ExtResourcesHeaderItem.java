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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.WicketAjaxJQueryResourceReference;
import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.js.ext.ExtBundle;
import org.wicketstuff.js.ext.ExtWicketAdapterBundle;

public class ExtResourcesHeaderItem extends HeaderItem {

    private static final ExtResourcesHeaderItem INSTANCE = new ExtResourcesHeaderItem();

    public static ExtResourcesHeaderItem get() {
        return INSTANCE;
    }

    private ExtResourcesHeaderItem() {}

    @Override
    public List<? extends HeaderItem> getProvidedResources() {
        List<HeaderItem> resources = new ArrayList<HeaderItem>();

        String extBase, extAll, patchedExtAjax, extAdapt;
        if (Application.get().getConfigurationType().equals(RuntimeConfigurationType.DEVELOPMENT)) {
            extBase = ExtBundle.EXT_BASE_DEBUG;
            extAll = ExtBundle.EXT_ALL_DEBUG;
            patchedExtAjax = ExtWicketAdapterBundle.PATCHED_EXT_BASE_AJAX;
            extAdapt = ExtWicketAdapterBundle.EXT_WICKET_ADAPTER_DEBUG;
        } else {
            extBase = ExtBundle.EXT_BASE_DEPLOY;
            extAll = ExtBundle.EXT_ALL_DEPLOY;
            patchedExtAjax = ExtWicketAdapterBundle.PATCHED_EXT_BASE_AJAX;
            extAdapt = ExtWicketAdapterBundle.EXT_WICKET_ADAPTER_DEPLOY;
        }
        resources.add(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(ExtBundle.class, extBase)));
        resources.add(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(ExtBundle.class, extAll)));
        resources.add(JavaScriptHeaderItem.forReference(WicketEventJQueryResourceReference.get()));
        resources.add(JavaScriptHeaderItem.forReference(WicketAjaxJQueryResourceReference.get()));
        resources.add(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(ExtBundle.class, patchedExtAjax)));
        resources.add(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(ExtBundle.class, extAdapt)));
        return resources;
    }

    @Override
    public Iterable<?> getRenderTokens() {
        return Arrays.asList("wicket-extjs-resources");
    }

    @Override
    public void render(final Response response) {
        for (HeaderItem item : getProvidedResources()) {
            item.render(response);
        }
    }
}
