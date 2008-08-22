package org.vraptor.introspector;

import org.vraptor.LogicRequest;

/**
 * A bean provider for inversion of control (dependency injection).
 * 
 * @author Guilherme Silveira
 * @since 2.2.4
 */
public interface BeanProvider {

	/**
	 * Search for a key in all scopes
	 * 
	 * @param context
	 *            the web context
	 * @param key
	 *            key
	 * 
	 * @return value
	 */
	Object findAttribute(LogicRequest context, String key);

}
