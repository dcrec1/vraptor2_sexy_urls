package org.vraptor.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.FieldOutjecter;
import org.vraptor.component.GetterOutjecter;
import org.vraptor.component.Outjecter;

/**
 * Wrapper class to deal with some reflection. TODO: this is too much procedural
 * work, should change to an instance. Take care of synchronization issues.
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class ReflectionUtil {

	private static final String IS_INITIALS = "is";

	private static final String GET_INITIALS = "get";

	private static final Logger LOG = Logger.getLogger(ReflectionUtil.class);

	/**
	 * Instantiates a class.
	 *
	 * @return the new object
	 * @throws ComponentInstantiationException
	 *             if something wrong occurs
	 */
	public static <T> T instantiate(Class<T> clazz) throws ComponentInstantiationException {
		try {
			return clazz.getConstructor().newInstance();
		} catch (InvocationTargetException e) {
			throw new ComponentInstantiationException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new ComponentInstantiationException(e.getMessage(), e);
		}
	}

	/**
	 * Wrapper for method invocation
	 *
	 * @param object
	 *            object
	 * @param method
	 *            method
	 * @param parameters
	 *            parameters
	 * @return the method's return
	 * @throws MethodInvocationException
	 */
	public static Object invoke(Object object, Method method, Object... parameters) throws MethodInvocationException {
		if(object == null) {
			throw new NullPointerException("Cannot invoke a method on a null instance");
		}
		if(method == null) {
			throw new NullPointerException("Cannot invoke a null method");
		}
		if(parameters == null) {
			throw new NullPointerException("Cannot invoke a method with null parameters");
		}
		try {
			return method.invoke(object, parameters);
		} catch (InvocationTargetException e) {
			throw new MethodInvocationException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new MethodInvocationException("Unable to execute " + method.getName() + ": " + e.getMessage(), e);
		}
	}

	/**
	 * Sets a field (must be accessible)
	 *
	 * @param component
	 *            component
	 * @param field
	 *            field
	 * @param value
	 *            new value
	 * @throws SettingException
	 *             unable to call setter
	 */
	public static void setField(Object component, Field field, Object value) throws SettingException {
		try {
			field.set(component, value);
		} catch (IllegalArgumentException e) {
			String v = value == null ? "null" : value.getClass().getName();
			throw new SettingException("Unable to set field " + field.getName() + ": " + e.getMessage() + " with " + v,
					e);
		} catch (IllegalAccessException e) {
			String v = value == null ? "null" : value.getClass().getName();
			throw new SettingException("Unable to set field " + field.getName() + ": " + e.getMessage() + " with " + v,
					e);
		}
	}

	public static Object get(Object component, Field field) throws GettingException {
		try {
			return field.get(component);
		} catch (IllegalAccessException e) {
			throw new GettingException("Unable to get field " + field.getName() + ": " + e.getMessage(), e);
		}
	}

	public static Method findGetter(Class type, String property) throws MethodInvocationException {
		String methodName = GET_INITIALS + Character.toUpperCase(property.charAt(0)) + property.substring(1);
		try {
			return type.getMethod(methodName);
		} catch (SecurityException e) {
			throw new MethodInvocationException("Unable to get getter method " + property, e);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Retrieves the setter method in an object for the specified property
	 *
	 * @param current
	 *            the current object
	 * @param property
	 *            the setter to find
	 * @return the setter or null if not found
	 */
	public static Method findSetter(Object current, String property) {
		String methodName = "set" + upperCaseFirstLetter(property);
		for (Method m : current.getClass().getMethods()) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	private static String upperCaseFirstLetter(String property) {
		return Character.toUpperCase(property.charAt(0)) + property.substring(1);
	}

	/**
	 * Read all field annotations from one type
	 */
	public static <T extends Annotation> List<FieldAnnotation<T>> readAnnotations(Class type, Class<T> annot) {
		List<FieldAnnotation<T>> list = new ArrayList<FieldAnnotation<T>>();
		for (Field f : type.getDeclaredFields()) {
			if (!f.isAnnotationPresent(annot)) {
				continue;
			}
			T annotation = f.getAnnotation(annot);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Adding field annotation on field " + f.getName() + "::" + annotation);
			}
			list.add(new FieldAnnotation<T>(annotation, f));
			f.setAccessible(true);
		}
		return list;
	}

	/**
	 * Tries to instantiate an array of size 0 or type
	 *
	 * @param type
	 *            type
	 * @return instantiates
	 * @throws ComponentInstantiationException
	 *             problem instantiating it
	 */
	public static Object genericInstantiate(Class<?> type) throws ComponentInstantiationException {
		if (type.isArray()) {
			return Array.newInstance(type.getComponentType(), 0);
		}
		if (Calendar.class.isAssignableFrom(type)) {
			return new GregorianCalendar();
		}
		return instantiate(type);
	}

	public static Object instantiateCollection(Type type) throws ComponentInstantiationException {
		Class clazz = (Class) type;
		if (List.class.isAssignableFrom(clazz)) {
			return new ArrayList();
		}
		if (Set.class.isAssignableFrom(clazz)) {
			return new LinkedHashSet();
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return new HashMap();
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			return new ArrayList();
		}
		throw new ComponentInstantiationException("Unable to instantiate the desired collection");
	}

	/**
	 * Tries to find an specific annotation inside an array
	 *
	 * @param <T>
	 *            the annotation type
	 * @param annotations
	 *            the array
	 * @param clazz
	 *            the annotation class to be found
	 * @return the annotation found or null if not found
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T findAnnotation(Annotation[] annotations, Class<T> clazz) {
		for (Annotation a : annotations) {
			if (a.annotationType().equals(clazz)) {
				return (T) a;
			}
		}
		return null;
	}

	/**
	 * Tries to instantiate this class
	 *
	 * @param class
	 *            the class to instantiate
	 * @return the instance
	 * @throws ComponentInstantiationException
	 */
	public static Object instantiate(String clazz) throws ComponentInstantiationException {
		try {
			return instantiate(Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			throw new ComponentInstantiationException("Unable to instantiate " + clazz, e);
		}
	}

	/**
	 * Returns a prefixed method.
	 *
	 * @param clazz
	 *            the clazz where we are looking for a method
	 * @param prefix
	 *            the method prefix
	 * @param name
	 *            the suffix
	 * @return the found prefixed method
	 * @throws MethodInvocationException
	 *             unsufficient rights to look for such method
	 */
	public static Method getPrefixedMethod(Class<?> clazz, String prefix, String name, Class... parameterTypes) {
		String methodName = prefix + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(methodName)) {
				if (Arrays.equals(m.getParameterTypes(), parameterTypes)) {
					return m;
				}
			}
		}
		return null;
	}

	public static Field getField(Class containingType, String field) throws GettingException {
		try {
			Field f = containingType.getDeclaredField(field);
			f.setAccessible(true);
			return f;
		} catch (SecurityException e) {
			throw new GettingException("Unable to validate field " + field, e);
		} catch (NoSuchFieldException e) {
			throw new GettingException("Unable to validate field " + field, e);
		}
	}

	/**
	 * Returns all getter (and iser) methods from the given class, excluding
	 * inherited ones, mapped by its property name. Ignores any method simply
	 * called "get".
	 *
	 * <pre>
	 *              name -&gt; getName
	 *              closed -&gt; isClosed
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 */
	public static Map<String, Method> getGetters(Class clazz) {
		if (!Modifier.isPublic(clazz.getModifiers())) {
			throw new IllegalArgumentException("class not public " + clazz);
		}
		Map<String, Method> methods = new TreeMap<String, Method>();
		for (Method m : clazz.getMethods()) {
			if (!isGetter(m)) {
				continue;
			}
			if (m.getDeclaringClass().equals(Object.class)) {
				// hack: removing getClass()
				continue;
			}
			String propertyName = "";
			if (m.getName().startsWith(GET_INITIALS)) {
				propertyName = m.getName().substring(GET_INITIALS.length());

			} else if (m.getName().startsWith(IS_INITIALS)) {
				propertyName = m.getName().substring(IS_INITIALS.length());
			}
			// ok, this is a hack, cause we can have a problem
			// with classes with a get() method
			// (the propertyname would be an empty string)
			if (propertyName.length() != 0) {
				if (propertyName.length() == 1 || Character.isLowerCase(propertyName.charAt(1))) {
					propertyName = StringUtil.classNameToInstanceName(propertyName);
				}
				methods.put(propertyName, m);
			}
		}
		return methods;
	}

	public static boolean isGetter(Method m) {
		if (m.getParameterTypes().length != 0 || !Modifier.isPublic(m.getModifiers())
				|| m.getReturnType().equals(Void.TYPE)) {
			return false;
		}
		if (Modifier.isStatic(m.getModifiers()) || !Modifier.isPublic(m.getModifiers())
				|| Modifier.isAbstract(m.getModifiers())) {
			return false;
		}
		if (m.getName().startsWith(GET_INITIALS) && m.getName().length() > GET_INITIALS.length()) {
			return true;
		}
		if (m.getName().startsWith(IS_INITIALS) && m.getName().length() > IS_INITIALS.length()
				&& (m.getReturnType().equals(boolean.class) || m.getReturnType().equals(Boolean.class))) {
			return true;
		}
		return false;
	}

	public static <T> List<Outjecter> loadOutjecters(Class<T> type) {
		List<Outjecter> outjections = new ArrayList<Outjecter>();
		for (Entry<String, Method> entry : ReflectionUtil.getGetters(type).entrySet()) {
			outjections.add(new GetterOutjecter(entry.getKey(), entry.getValue()));
		}
		for (FieldAnnotation<Out> out : ReflectionUtil.readAnnotations(type, Out.class)) {
			outjections.add(new FieldOutjecter(out));
		}
		return outjections;
	}

}