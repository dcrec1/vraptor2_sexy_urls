package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vraptor.Interceptor;

/**
 * Sets a list of interceptors for a class.
 * 
 * @author Guilherme Silveira
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface InterceptedBy {
	/**
	 * Interceptor's list
	 * 
	 * @return list
	 */
	public Class<? extends Interceptor>[] value();
}
