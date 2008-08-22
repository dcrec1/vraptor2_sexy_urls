package org.vraptor.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

import org.vraptor.component.ComponentInstantiationException;

/**
 * Collection implementation.
 * 
 * @author Guilherme Silveira
 */
public class CollectionDealer implements SetDealer {

	@SuppressWarnings("unchecked")
	public Object resizeAndSet(Object object, int position, boolean mayCreate,
			Type type) throws SettingException {
		int length = position + 1;

		Collection collection = (Collection) object;
		int realLen = collection.size();

		if (realLen >= length) {
			return object;
		}

		if ((!mayCreate) || length != realLen + 1) {
			throw new SettingException(
					"Invalid array index - probably missed one object in between: "
							+ length);
		}

		Class desiredClass = (type instanceof ParameterizedType) ? (Class) ((ParameterizedType) type)
				.getActualTypeArguments()[0]
				: Object.class;

		try {
			collection.add(ReflectionUtil.instantiate(desiredClass));
		} catch (ComponentInstantiationException e) {
			throw new SettingException(e.getMessage(), e);
		}
		return object;

	}

	public Object getPosition(Object object, int arrayPosition,
			boolean mayCreate) throws SettingException {
		Collection collection = (Collection) object;
		if (arrayPosition >= collection.size()) {
			throw new SettingException("Invalid collection index: "
					+ arrayPosition);
		}
		return iterate((Collection) object, arrayPosition);
	}

	/**
	 * Iterates over a collection n times
	 * 
	 * @param collection
	 *            the collection
	 * @param index
	 *            n
	 * @return the object at this position
	 */
	Object iterate(Collection set, int index) {
		Iterator it = set.iterator();
		for (int i = 0; i < index; i++, it.next()) {
			;
		}
		return it.next();
	}

	@SuppressWarnings("unchecked")
	public Object resizeAndSet(Object obj, int position, Object value)
			throws SettingException {
		Collection collection = (Collection) obj;
		if (collection.size() > position) {
			throw new SettingException(
					"There is already an object in this position!");
		}
		if (collection.size() != position) {
			throw new SettingException(
					"Invalid array index - probably missed one object in between: "
							+ position);
		}
		collection.add(value);
		return obj;
	}

	public Class getType(Type type) {
		return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
	}

}
