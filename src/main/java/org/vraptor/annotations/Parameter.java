package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to read parameters from request.
 * 
 * @author Guilherme Silveira
 * @since 2.2
 */
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Parameter {
	/**
	 * Creates objects as needed.
	 */
	boolean create() default false;

	/**
	 * The parameter key to be used.
	 * 
	 * @return the key itself
	 */
	String key() default "";
}
