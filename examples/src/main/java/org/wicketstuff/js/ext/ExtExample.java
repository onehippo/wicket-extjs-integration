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
