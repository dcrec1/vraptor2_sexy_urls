package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Marks a logic method to give permission to serialize its results
 * for remote clients, like AJAX and XML consumers
 *  
 * @author Paulo Silveira
 *
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Remotable {
	/**
	 * How deep should the outjected results be serialized.
	 * It must be a positive value. Be sure to keep this
	 * value as low as possible. The default value is 4.
	 * 
	 */
	int depth() default 4;
}
