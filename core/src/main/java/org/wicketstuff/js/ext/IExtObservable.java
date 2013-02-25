package org.wicketstuff.js.ext;

import java.io.Serializable;

import org.wicketstuff.js.ext.util.ExtEventListener;

public interface IExtObservable extends Serializable {

    void addEventListener(String eventName, ExtEventListener listener);

}
