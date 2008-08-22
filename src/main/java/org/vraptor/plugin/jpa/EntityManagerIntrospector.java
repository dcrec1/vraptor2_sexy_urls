package org.vraptor.plugin.jpa;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;

/**
 * Reads JPA related metadata on {@link ComponentType}s
 *
 * @author Fabio Kung
 * @since 2.3.2
 */
class EntityManagerIntrospector {

	public static final String DEFAULT_PERSISTENCE_UNIT = "default";

	/**
	 *
	 * @param componentType
	 * @return the {@link PersistenceContext} annotated field if exists, null
	 *         otherwise
	 */
	public Field getEntityManagerField(ComponentType componentType) {
		Class<?> componentClass = componentType.getComponentClass();
		for (Field field : componentClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(PersistenceContext.class)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Checks if the component has any constructor dependency on
	 * {@link EntityManager}
	 *
	 * @param component
	 * @return true if the component has constructor dependency
	 */
	public boolean dependsOnEntityManager(ComponentType component) {
		for (Constructor constructor : component.getComponentClass().getConstructors()) {
			for (Class<?> dependency : constructor.getParameterTypes()) {
				if (EntityManager.class.equals(dependency)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasPersistenceContext(ComponentType component) {
		for (Field field : component.getComponentClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(PersistenceContext.class)) {
				return true;
			}

		}
		return false;
	}

	public String getPersistenceUnitName(ComponentType componentType) {
		String persistenceUnit = DEFAULT_PERSISTENCE_UNIT;
		Field entityManagerField = this.getEntityManagerField(componentType);

		if (entityManagerField != null) {
			PersistenceContext persistenceContext = entityManagerField.getAnnotation(PersistenceContext.class);
			if (persistenceContext.unitName().trim().length() != 0) {
				persistenceUnit = persistenceContext.unitName();
			}

		}
		return persistenceUnit;
	}

	public boolean isTransactionRequired(LogicMethod method) {
		Transaction transactionMetadata = method.getMetadata().getAnnotation(Transaction.class);
		return transactionMetadata != null && transactionMetadata.required();
	}

}
