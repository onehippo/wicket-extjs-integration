package de.fj.wickx.util;


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
