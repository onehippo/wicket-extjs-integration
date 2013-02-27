package org.wicketstuff.js.ext;

import org.wicketstuff.js.ext.util.ExtClass;
import org.wicketstuff.js.ext.util.ExtMethod;

@ExtClass("Ext.MessageBox")
public class ExtMessageBox {
	
	public enum Buttons {
		OK, OKCANCEL, YESNOCANCEL 
	}
	
	public enum Icons {
		ERROR, INFO, QUESTION, WARNING
	}
	
	@ExtMethod
	public static void show(ExtMessageBoxOptions options) {
		
	}
	
	@ExtMethod
	public static void confirm(String title, String msg, ExtMessageBoxCallback fn) {
		 
	}

	@ExtMethod
	public static void prompt(String title, String msg, ExtMessageBoxCallback fn) {
		
	}
	
	@ExtMethod
	public static void alert(String title, String msg, ExtMessageBoxCallback fn) {
		
	}
	
	@ExtMethod
	public static void hide() {
		
	}
	
	@ExtMethod
	public static void updateProgress(Number value, String progressText, String msg) {
		
	}

	
	
}
