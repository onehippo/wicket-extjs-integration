package org.wicketstuff.js.ext.util;

public abstract class ExtFormat {

	public static final ExtFormat usMoney = new ExtFormat() {
		@Override
		public String get() {
			return "Ext.util.Format.usMoney";
		}
	};

	public static ExtFormat dateRenderer(final String format) {
		return new ExtFormat() {

			@Override
			public String get() {
				return "Ext.util.Format.dateRenderer('" + format + "')";
			}

		};
	}

	public abstract String get();

}
