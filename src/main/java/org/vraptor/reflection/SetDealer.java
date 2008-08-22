package org.vraptor.reflection;

import java.lang.reflect.Type;


/**
 * A generic set dealer.
 * 
 * @author Guilherme Silveira
 */
public interface SetDealer {

	/**
	 * Resizes if needed (per one) and sets the object 
	 */
	Object resizeAndSet(Object object, int position, boolean mayCreate, Type type)
			throws SettingException;

	Object getPosition(Object object, int arrayPosition, boolean mayCreate)
			throws SettingException;

	Object resizeAndSet(Object component, int arrayPosition, Object value)
			throws SettingException;

	Class getType(Type type);

}
