package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the presentation layer that there is no view associated with this
 * bussiness logic. This is typically the case where a file download is made
 * using the client's OutputStream in a programatic way.
 * 
 * @author Guilherme Silveira
 */
@Target(ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Viewless {
}
