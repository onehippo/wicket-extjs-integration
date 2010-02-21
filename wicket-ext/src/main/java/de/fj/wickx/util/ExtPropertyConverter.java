package de.fj.wickx.util;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtPropertyConverter {
	public static Object convert(Object property) {
		if (property instanceof IModel<?>) {
			return ((IModel<?>) property).getObject();
		} else if (property.getClass().isEnum()) {
			return property.toString().toLowerCase();
		} else {
			return property;
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
	
	public static String generateArgs(Object[] methodArguments) {
		String[] argsArray = new String[methodArguments.length];
		for (int i = 0; i < methodArguments.length; ++i) {
			Object value = convert(methodArguments[i]);
			if (value instanceof String) {
				argsArray[i] = JSONObject.quote(value.toString());
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
