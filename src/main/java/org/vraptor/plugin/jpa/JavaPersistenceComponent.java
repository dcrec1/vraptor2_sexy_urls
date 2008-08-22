package org.vraptor.plugin.jpa;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;

import org.vraptor.component.BeanConstructor;
import org.vraptor.component.ComponentType;
import org.vraptor.component.ComponentTypeWrapper;
import org.vraptor.interceptor.InterceptorType;

/**
 * Wraps the standard {@link ComponentType} providing JPA support and
 * {@link EntityManager} injection.
 *
 * @author Fabio Kung
 * @since 2.3.2
 */
class JavaPersistenceComponent extends ComponentTypeWrapper {

	private EntityManagerIntrospector introspector;

	JavaPersistenceComponent(ComponentType component, EntityManagerIntrospector introspector) {
		super(component);
		this.introspector = introspector;
	}

	@Override
	public List<InterceptorType> getInterceptors() {
		List<InterceptorType> interceptors = super.getInterceptors();
		InterceptorType interceptorType = InterceptorType.getType(JavaPersistenceInterceptor.class);
		if (!interceptors.contains(interceptorType)) {
			interceptors.add(interceptorType);
		}
		return interceptors;
	}

	@Override
	public BeanConstructor getConstructor() {
		Field field = this.introspector.getEntityManagerField(this);
		// wrap if the component has any @PersistenceContext
		BeanConstructor constructor;
		if (field == null) {
			constructor = super.getConstructor();
		} else {
			constructor = new EntityManagerInjector(super.getConstructor(), this.introspector);
		}

		return constructor;
	}
}
