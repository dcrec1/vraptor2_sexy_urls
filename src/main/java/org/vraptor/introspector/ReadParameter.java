package org.vraptor.introspector;

import java.lang.reflect.Type;

import org.vraptor.converter.Converter;
import org.vraptor.reflection.SettingException;

/**
 * Parameters to be read in order to execute a logic.
 * 
 * @author Guilherme Silveira
 */
public interface ReadParameter {

	/**
	 * The parameter key
	 * 
	 * @return key
	 */
	String getKey();

	/**
	 * Should it instantiate required fields on the fly?
	 * 
	 * @return true if it should instantiate them
	 */
	boolean mightCreate();

	/**
	 * Returns the overriden converter class attached to this read parameter
	 * 
	 * @return the converter class
	 */
	Class<? extends Converter> getOverridenConverter();

	/**
	 * Guarantees it's existence by instantiating itself if needed (or oblied),
	 * connecting to the logic component and returning itself
	 * 
	 * @param component
	 *            the logic component
	 * @return the instantiated object itself
	 * @throws SettingException
	 *             some problem during its creation
	 */
	Object guaranteeExistence(Object component,Object[] methodParams) throws SettingException;

	Class<?> getType();

	void set(Object component, Object[] methodParams, Object value) throws SettingException;

	Type getGenericType();

}
