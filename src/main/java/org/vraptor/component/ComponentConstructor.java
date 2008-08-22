package org.vraptor.component;

import java.lang.reflect.Constructor;

import org.vraptor.LogicRequest;
import org.vraptor.introspector.BeanProvider;

/**
 * A component constructor. It allows users to instantiate a component type
 * using an specific bean provider.
 * 
 * @author Guilherme Silveira
 */
public class ComponentConstructor implements BeanConstructor {

	private final Constructor constructor;

	private final Class[] types;

	private final String[] keys;

	ComponentConstructor(Constructor constructor) {
		this.constructor = constructor;
		this.types = constructor.getParameterTypes();
		this.keys = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			keys[i] = types[i].getName();
		}
	}

	/* (non-Javadoc)
	 * @see org.vraptor.component.BeanConstructor#newInstance(org.vraptor.LogicRequest, org.vraptor.introspector.BeanProvider)
	 */
	public Object newInstance(LogicRequest request, BeanProvider provider) throws ComponentInstantiationException {
		Object[] values = new Object[keys.length];
		for (int i = 0; i < keys.length; i++) {
			values[i] = provider.findAttribute(request, keys[i]);
			if (values[i] == null) {
				throw new ComponentInstantiationException("Unable to fill value for key " + keys[i]);
			}
		}
		try {
			return constructor.newInstance(values);
		} catch (Exception e) {
			throw new ComponentInstantiationException("Unable to instantiate using " + constructor, e);
		}
	}

	@Override
	public String toString() {
		return constructor.getName() + keys.toString();
	}

}
