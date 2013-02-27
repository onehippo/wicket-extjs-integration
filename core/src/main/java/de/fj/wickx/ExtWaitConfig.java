package de.fj.wickx;

import de.fj.wickx.util.ExtOptions;
import de.fj.wickx.util.ExtProperty;

@ExtOptions
public class ExtWaitConfig {

	@ExtProperty
	protected Number interval;
	
	public void setInterval(Number interval) {
		this.interval = interval;
	}
	
}
