package org.vraptor.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import org.vraptor.component.ComponentInstantiationException;

/**
 * An array implementation.
 * 
 * @author Guilherme Silveira
 */
public class ArrayDealer implements SetDealer {

	public Object resizeAndSet(Object object, int position, boolean mayCreate,
			Type type) throws SettingException {

		int length = position + 1;

		if (object == null) {
			if (!mayCreate) {
				throw new SettingException("Unable to create array");
			}
			object = Array.newInstance((Class<?>) type, 0);
		}

		int realLen = Array.getLength(object);

		if (realLen >= length) {
			return object;
		}

		if ((!mayCreate) || length != realLen + 1) {
			throw new SettingException(
					"Invalid array index - probably missed one object in between: "
							+ position);
		}

		// reinstantiates the array
		Object newArray = Array.newInstance(object.getClass()
				.getComponentType(), length);
		for (int j = 0; j < realLen; j++) {
			Array.set(newArray, j, Array.get(object, j));
		}
		try {
			Array.set(newArray, position, ReflectionUtil.instantiate(object
					.getClass().getComponentType()));
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e);
		}
		return newArray;

	}

	public Object getPosition(Object object, int arrayPosition,
			boolean mayCreate) {
		return Array.get(object, arrayPosition);
	}

	public Object resizeAndSet(Object array, int position, Object value)
			throws SettingException {
		int len = Array.getLength(array);
		if (len > position) {
			throw new SettingException(
					"There is already an object in this position!");
		}
		if (len != position) {
			throw new SettingException(
					"Invalid array index - probably missed one object in between: "
							+ position);
		}
		Object newArray;
		if (len < position + 1) {
			// reinstantiates the array if needed
			newArray = Array.newInstance(array.getClass().getComponentType(),
					position + 1);
			for (int j = 0; j < Array.getLength(array); j++) {
				Array.set(newArray, j, Array.get(array, j));
			}
		} else {
			newArray = array;
		}
		Array.set(newArray, position, value);
		return newArray;
	}

	public Class getType(Type type) {
		return ((Class) type).getComponentType();
	}

}
