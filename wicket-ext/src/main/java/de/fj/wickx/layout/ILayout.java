package de.fj.wickx.layout;

import java.io.Serializable;

import de.fj.wickx.ExtComponent;

public interface ILayout extends Serializable {

	void applyLayout(ExtComponent component);

}
