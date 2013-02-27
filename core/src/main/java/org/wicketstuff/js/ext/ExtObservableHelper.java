package org.wicketstuff.js.ext;

import java.util.Locale;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.resource.IPropertiesFactory;
import org.apache.wicket.resource.Properties;
import org.apache.wicket.util.resource.locator.ResourceNameIterator;
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
            ResourceNameIterator iter = new ResourceNameIterator(path, style, locale, null);
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
