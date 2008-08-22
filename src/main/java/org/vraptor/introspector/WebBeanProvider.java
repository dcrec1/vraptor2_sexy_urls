package org.vraptor.introspector;

import org.vraptor.LogicRequest;

/**
 * A basic bean provider which looks for components in all scopes.
 * 
 * @author Guilherme Silveira
 * @since 2.2.4
 */
public class WebBeanProvider implements BeanProvider {

	public Object findAttribute(LogicRequest context, String key) {
		return context.findAttribute(key);
	}

}
