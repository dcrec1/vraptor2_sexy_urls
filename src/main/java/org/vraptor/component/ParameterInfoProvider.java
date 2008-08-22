package org.vraptor.component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Instances of a class implementing this interface are capable of answering which method parameters this method contains.
 * Typical implementations would return all method parameters with their respective names using reflection, conventions, configuration or asm. 
 * @author Guilherme Silveira
 *  @since 2.5.1
 */
public interface ParameterInfoProvider {

	/**
	 * Returns a list with all method parameters for this method.
	 */
	List<MethodParameter> provideFor(Method method);

}
