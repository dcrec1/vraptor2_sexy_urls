package org.vraptor.component;

import org.vraptor.LogicRequest;
import org.vraptor.introspector.BeanProvider;

/**
 * A basic bean constructor.
 * 
 * @author Guilherme Silveira
 * @since 2.2.4
 */
public interface BeanConstructor {

	/**
	 * Instantiates this component.
	 * 
	 * @param request
	 *            the request scope
	 * @return the new instance
	 * @throws ComponentInstantiationException
	 *             any error
	 */
	Object newInstance(LogicRequest request, BeanProvider provider) throws ComponentInstantiationException;

}