package org.vraptor.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.component.Clazz;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.Outjectable;
import org.vraptor.component.Outjecter;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ScopeType;

/**
 * Interceptor class to deal with interceptors instances
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class InterceptorType implements Outjectable {

	private static final Logger LOG = Logger.getLogger(InterceptorType.class);

	private static final ConcurrentMap<Class, InterceptorType> types = new ConcurrentHashMap<Class, InterceptorType>();

	private final Clazz type;

	private final List<Outjecter> outjections;

	/** In annotations */
	private final List<FieldAnnotation<In>> inAnnotations;

	/**
	 * New interceptor class
	 *
	 * @param type
	 *            class
	 */
	private InterceptorType(Class<? extends Interceptor> type) {
		this.type = new Clazz(type);
		this.inAnnotations = ReflectionUtil.readAnnotations(this.type.getType(), In.class);
		this.outjections = ReflectionUtil.loadOutjecters(type);
	}

	/**
	 * Returns a new instance of this interceptor, using the specified bean
	 * provider.
	 *
	 * @since 2.3.2
	 */
	public Interceptor newInstance(LogicRequest logicRequest, BeanProvider provider)
			throws ComponentInstantiationException, InvalidComponentException {
		return (Interceptor) type.findSingleConstructor().newInstance(logicRequest, provider);
	}

	/**
	 * @return Returns the inAnnotations.
	 */
	public List<FieldAnnotation<In>> getInAnnotations() {
		return this.inAnnotations;
	}

	public Class getInterceptorClass() {
		return this.type.getType();
	}

	/**
	 * Returns an InterceptorType which represents the given interceptor class.
	 *
	 * @param c
	 * @return
	 */
	public static InterceptorType getType(Class<? extends Interceptor> c) {
		if (!types.containsKey(c)) {
			LOG.debug("creating interceptor type for " + c);
			types.put(c, new InterceptorType(c));
		}
		return types.get(c);
	}

	/**
	 * Returns an InterceptorType which represents the given interceptor
	 *
	 * @param interceptor
	 * @return
	 */
	public static InterceptorType getType(Interceptor interceptor) {
		return getType(interceptor.getClass());
	}

	/**
	 * Returns a list of interceptors for the specified component class, parsing
	 * its InterceptedBy annotations
	 *
	 * @param clazz
	 *            class
	 * @return interceptor type list
	 */
	public static List<InterceptorType> getInterceptors(Class<?> clazz) {
		List<InterceptorType> types = new ArrayList<InterceptorType>();
		if (clazz.isAnnotationPresent(InterceptedBy.class)) {
			@SuppressWarnings("unchecked")
			InterceptedBy annotation = clazz.getAnnotation(InterceptedBy.class);
			for (Class<? extends Interceptor> c : annotation.value()) {
				InterceptorType type = InterceptorType.getType(c);
				types.add(type);
			}
		}
		return types;
	}

	public Map<String, Object> getOutjectedValues(Object comp, ScopeType scope) throws GettingException,
			MethodInvocationException {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Outjecter out : outjections) {
			if (out.getScope().equals(scope)) {
				String key = out.getKey();
				Object obj = out.getValue(comp);
				map.put(key, obj);
			}
		}
		return map;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof InterceptorType) {
            InterceptorType other = (InterceptorType) o;
            return this.type.equals(other.type);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.type.hashCode();
    }
}
