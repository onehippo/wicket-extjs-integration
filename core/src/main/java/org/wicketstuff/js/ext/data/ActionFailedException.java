package org.wicketstuff.js.ext.data;

/**
 * Thrown to indicate that an action failed.
 */
public class ActionFailedException extends Exception {

    public ActionFailedException(final String message) {
        super(message);
    }

    public ActionFailedException(final String message, Throwable cause) {
        super(message, cause);
    }

}
