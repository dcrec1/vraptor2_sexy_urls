package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vraptor.converter.Converter;

/**
 * Overrides a converter for an specific property.
 * 
 * @author Guilherme Silveira
 */
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conversion {

	/**
	 * The converter to be used
	 * 
	 * @return converter
	 */
	public Class<? extends Converter> value();

}
