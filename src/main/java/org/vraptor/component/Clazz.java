package org.vraptor.component;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.vraptor.reflection.ReflectionUtil;

/**
 * Wrapper for reflecting on Class with generic functionalities.
 * 
 * @author Guilherme Silveira
 * @since 2.3.1
 */
public class Clazz<T> {

	private final Class<T> type;

	public Clazz(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	/**
	 * Creates a new instance from this type, using a default implementation of
	 * a collection when needed. If the type is a primitive, return its default value.
	 * 
	 * @return the new instance
	 * @throws ComponentInstantiationException
	 */
	public Object newInstance() throws ComponentInstantiationException {
		if(this.type.isPrimitive()) {
			if(this.type.equals(boolean.class)) {
				return false;
			} else if(this.type.equals(int.class)) {
				return 0;
			} else if(this.type.equals(long.class)) {
				return 0L;
			} else if(this.type.equals(double.class)) {
				return 0d;
			} else if(this.type.equals(short.class)) {
				return (short) 0;
			} else if(this.type.equals(float.class)) {
				return 0f;
			} else if(this.type.equals(char.class)) {
				return (char)(0);
			} else if(this.type.equals(byte.class)) {
				return (byte) 0;
			}
		}
		if (Calendar.class.isAssignableFrom(this.type)) {
			return ReflectionUtil.instantiate(GregorianCalendar.class);
		}
		if (Collection.class.isAssignableFrom(this.type)) {
			return ReflectionUtil.instantiateCollection(this.type);
		}
		return ReflectionUtil.instantiate(this.type);
	}

	/**
	 * Returns the single constructor - if there is only one.
	 * @return	the single constructor.
	 * @throws InvalidComponentException	if there is more than one constructor.
	 */
	public BeanConstructor findSingleConstructor() throws InvalidComponentException {
		Constructor[] constructors = type.getConstructors();
		if (constructors.length != 1) {
			throw new InvalidComponentException(
					type.getName()
							+ " component has "
							+ constructors.length
							+ " accessible constructors. This is not desirable as it may reflect optional arguments and create complex attributes.");
		}
		return new ComponentConstructor(constructors[0]);
	}

}
