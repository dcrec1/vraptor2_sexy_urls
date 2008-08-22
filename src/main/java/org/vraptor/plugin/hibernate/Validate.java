package org.vraptor.plugin.hibernate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes that VRaptor should validate some fields or parameters.
 * 
 * @author Guilherme Silveira
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validate {

	/**
	 * Field names to be validated
	 * 
	 * @return the list of fields to be validated
	 */
	public String[] fields() default {};

	/**
	 * Parameters to be validated.
	 * 
	 * @return the list of parameters to be validated
	 * @since 2.2.4
	 */
	public String[] params() default {};

}
