package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a method as a component destroy call. When this method is called,
 * the component is removed from the specified scope. The default exit method is
 * called destroy.
 * 
 * @author Guilherme Silveira
 * @deprecated
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface Destroy {

}
