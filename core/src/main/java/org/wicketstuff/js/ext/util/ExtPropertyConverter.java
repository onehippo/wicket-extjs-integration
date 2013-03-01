package org.wicketstuff.js.ext.util;

import java.lang.reflect.Field;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.wicketstuff.js.ext.ExtComponent;
import org.wicketstuff.js.ext.ExtMessageBox.Buttons;
import org.wicketstuff.js.ext.ExtMessageBoxCallback;

public class ExtPropertyConverter {
    public static Object convert(Object property) {
        if (property == null) {
            return null;
        } else if (property instanceof IModel<?>) {
            return ((IModel<?>) property).getObject();
        } else if (property instanceof Buttons) {
            return new JSONIdentifier("Ext.MessageBox." + property.toString());
        } else if (property.getClass().isEnum()) {
            return property.toString().toLowerCase();
        } else if (property instanceof ExtFormat) {
            return new JSONIdentifier(((ExtFormat) property).get());
        } else if (property.getClass().isAnnotationPresent(ExtOptions.class)) {
            JSONObject options = new JSONObject();
            addProperties(property, property.getClass(), options);
            return options;
        } else if (property instanceof Component) {
            return ((Component) property).getMarkupId();
        } else if (property instanceof ExtMessageBoxCallback) {
            return new JSONIdentifier(property.toString());
        } else {
            return property;
        }
    }

    public static void addProperties(Object object, Class<?> clazz, JSONObject properties) {
        Field fs[] = clazz.getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(ExtProperty.class)) {
                Object value = PropertyResolver.getValue(f.getName(), object);
                if (value != null) {
                    setIfNotNull(properties, f.getName(), convert(value));
                }
            }
        }
        if (ExtComponent.class.isAssignableFrom(clazz.getSuperclass())) {
            addProperties(object, clazz.getSuperclass(), properties);
        }
    }

    public static void setIfNotNull(JSONObject object, String key, Object value) {
        if (value != null) {
            try {
                object.put(key, convert(value));
            } catch (JSONException e) {
                throw new WicketRuntimeException(e);
            }
        }
    }

    public static String generateReferenceObject(Object object) {
        if (object instanceof ExtComponent) {
            ExtComponent target = (ExtComponent) object;
            return String.format("Ext.getCmp('%s')", target.getMarkupId());
        } else {
            return "";
        }
    }

    public static String generateStaticPart(Class<?> clazz) {
        ExtClass annotation = clazz.getAnnotation(ExtClass.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return "";
        }
    }

    public static String generateArgs(Object[] methodArguments) {
        String[] argsArray = new String[methodArguments.length];
        for (int i = 0; i < methodArguments.length; ++i) {
            Object value = convert(methodArguments[i]);
            if (value == null) {
                argsArray[i] = "null";
            } else if (value instanceof String) {
                argsArray[i] = JSONObject.quote(value.toString());
            } else if (value instanceof JSONString) {
                argsArray[i] = ((JSONString) value).toJSONString();
            } else {
                argsArray[i] = value.toString();
            }
        }
        StringBuffer args = new StringBuffer();
        for (int i = 0; i < argsArray.length; ++i) {
            if (i > 0) {
                args.append(",");
            }
            args.append(argsArray[i]);
        }
        return args.toString();
    }
}
