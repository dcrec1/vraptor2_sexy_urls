package org.vraptor.component;

import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;

/**
 * Responsible for outjecting objects.
 * 
 * @author Guilherme Silveira
 * @since 2.2.3
 */
public interface Outjecter {
	
	ScopeType getScope();

	String getKey();
	
	Object getValue(Object obj) throws GettingException, MethodInvocationException;
	
}
