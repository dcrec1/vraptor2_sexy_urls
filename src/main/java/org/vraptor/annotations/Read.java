package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to read parameters from request. Deprecated due to common
 * mistakes made by users. Use Parameter instead.
 * 
 * @author Guilherme Silveira
 * @author Paulo Silveira
 * @deprecated
 */
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Deprecated
public @interface Read {
	/**
	 * Creates objects as needed
	 */
	boolean create() default false;

	/**
	 * The parameter key to be used
	 * 
	 * @return the key itself
	 */
	String key() default "";
}
