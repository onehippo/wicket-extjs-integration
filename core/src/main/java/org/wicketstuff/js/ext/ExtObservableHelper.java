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

import java.util.Locale;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.resource.locator.ResourceNameIterator;
import org.apache.wicket.resource.IPropertiesFactory;
import org.apache.wicket.resource.Properties;
import org.apache.wicket.util.value.ValueMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.js.ext.util.ExtClass;

class ExtObservableHelper {

    private ExtObservableHelper() {
        // prevent instantiation
    }

    static JSONObject renderResources(final IExtObservable extObservable, Class<?> baseClass) throws JSONException {
        JSONObject resources = new JSONObject();

        IPropertiesFactory propertiesFactory = Application.get().getResourceSettings().getPropertiesFactory();
        Session session = Session.get();
        String style = session.getStyle();
        Locale locale = session.getLocale();

        Class<?> clazz = extObservable.getClass();
        while (clazz != baseClass) {
            String path = clazz.getName().replace('.', '/');
            ResourceNameIterator iter = new ResourceNameIterator(path, style, null, locale, null, false);
            while (iter.hasNext()) {
                String newPath = iter.next();

                final Properties props = propertiesFactory.load(clazz, newPath);
                if (props != null) {
                    ValueMap all = props.getAll();
                    for (Map.Entry<String, Object> entry : all.entrySet()) {
                        if (!resources.has(entry.getKey())) {
                            resources.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return resources;
    }

    public static String getExtClass(final IExtObservable observable, final Class<?> rootClass) {
        Class clazz = observable.getClass();
        do {
            ExtClass annotation = (ExtClass) clazz.getAnnotation(ExtClass.class);
            if (annotation != null) {
                return annotation.value();
            }
            clazz = clazz.getSuperclass();
        } while (rootClass.isAssignableFrom(clazz));

        throw new WicketRuntimeException("Could not find self in type hierarchy");
    }
}
