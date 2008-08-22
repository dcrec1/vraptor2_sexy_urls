package org.vraptor.plugin.jpa;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.introspector.BeanProvider;

/**
 * Wraps the standard {@link BeanConstructor} injecting an {@link EntityManager}
 * at the construction time.
 *
 * @author Fabio Kung
 * @since 2.3.2
 */
public class EntityManagerInjector implements BeanConstructor {

	private final EntityManagerIntrospector introspector;

	private final BeanConstructor beanConstructor;

	private static final Logger LOG = Logger.getLogger(EntityManagerInjector.class);

	public EntityManagerInjector(BeanConstructor constructor, EntityManagerIntrospector introspector) {
		this.beanConstructor = constructor;
		this.introspector = introspector;
	}

	public Object newInstance(LogicRequest request, BeanProvider provider) throws ComponentInstantiationException {
		Object instance = beanConstructor.newInstance(request, provider);
		injectPersistenceContext(request, instance);

		return instance;
	}

	private void injectPersistenceContext(LogicRequest request, Object component)
			throws ComponentInstantiationException {
		EntityManager entityManager = (EntityManager) request.getRequestContext().getAttribute(
				EntityManager.class.getName());

		Field field = introspector.getEntityManagerField(request.getLogicDefinition().getComponentType());
		if (field != null) {
			try {
				LOG.debug(MessageFormat.format("Injecting EntityManager in the component: {0}.{1}", field.getType()
						.getName(), field.getName()));
				field.setAccessible(true);
				field.set(component, entityManager);
			} catch (IllegalAccessException e) {
				throw new ComponentInstantiationException("Problem injecting the EntityManager", e);
			}
		}
	}

}
