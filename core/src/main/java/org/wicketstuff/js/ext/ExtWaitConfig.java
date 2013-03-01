package org.wicketstuff.js.ext;

import org.wicketstuff.js.ext.util.ExtOptions;
import org.wicketstuff.js.ext.util.ExtProperty;

@ExtOptions
public class ExtWaitConfig {

    @ExtProperty
    protected Number interval;

    public void setInterval(Number interval) {
        this.interval = interval;
    }

}
