package org.vraptor.plugin.spring;

import org.springframework.context.ApplicationContext;
import org.vraptor.LogicRequest;
import org.vraptor.introspector.BeanProvider;

/**
 * A spring based introspector. It will look for beans in the spring application
 * context before trying to use its wrapped introspector.
 * 
 * @author Guilherme Silveira
 * @since 2.2.4
 */
public class SpringProvider implements BeanProvider {

	private final BeanProvider provider;

	private final ApplicationContext springContext;

	public SpringProvider(BeanProvider provider, ApplicationContext context) {
		this.provider = provider;
		this.springContext = context;
	}

	public Object findAttribute(LogicRequest context, String key) {
		if (springContext.containsBean(key)) {
			return springContext.getBean(key);
		}
		return provider.findAttribute(context, key);
	}

}
