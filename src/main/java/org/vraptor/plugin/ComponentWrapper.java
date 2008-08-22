package org.vraptor.plugin;

import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicNotFoundException;

/**
 * A component wrapper. Classes which implement this interfaces decide wheter
 * components should be wrapped or not.
 *
 * @author Guilherme Silveira
 * @since 2.4
 */
public interface ComponentWrapper {

	boolean needsToWrap(ComponentType component) throws LogicNotFoundException;

	ComponentType wrap(ComponentType component);

}
