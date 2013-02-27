package de.fj.wickx.util;

import org.json.JSONString;

public class JSONIdentifier implements JSONString {
	
	private CharSequence name;

	public JSONIdentifier(CharSequence name) {
		this.name = name;
	}
	
	@Override
	public String toJSONString() {
		return name.toString();
	}

}
