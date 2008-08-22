package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vraptor.scope.ScopeType;

/**
 * Field outjection: describes a field as to be outjected after logic execution.
 * It also could be used to outject the return object of a method.
 *
 * @author Guilherme Silveira
 */
@Target( { ElementType.FIELD, ElementType.METHOD })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Out {
	/**
	 * Which scope should be used to outject the object
	 *
	 * @return scope
	 */
	public ScopeType scope() default ScopeType.REQUEST;

	/**
	 * Uses the field name if no value is provided
	 *
	 * @return default value is the field name itself
	 */
	public String key() default "";
}
