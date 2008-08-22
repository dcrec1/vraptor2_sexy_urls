package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vraptor.scope.ScopeType;

/**
 * Describes a field where injection should be performed.
 * 
 * @author Guilherme Silveira
 */
@Target(ElementType.FIELD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface In {
	/**
	 * Should it be created if not found?
	 * 
	 * @return true or false
	 */
	public boolean create() default false;

	/**
	 * Is it required?
	 * 
	 * @return true or false
	 */
	public boolean required() default true;

	/**
	 * Which scope should be used to search for the object
	 * 
	 * @return scope
	 */
	public ScopeType scope() default ScopeType.REQUEST;

	/**
	 * Which key should be used (default to field name)
	 * 
	 * @return key
	 */
	public String key() default "";
}
