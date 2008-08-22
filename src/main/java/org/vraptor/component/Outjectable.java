package org.vraptor.component;

import java.util.Map;

import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;

/**
 * Marks that a class has outjectable objects.
 * 
 * @author Guilherme Silveira
 * @since 2.2.3
 */
public interface Outjectable {

	/**
	 * Returns all outjected objects from an specific scope
	 */
	Map<String, Object> getOutjectedValues(Object comp, ScopeType scope) throws GettingException,
			MethodInvocationException;

}
