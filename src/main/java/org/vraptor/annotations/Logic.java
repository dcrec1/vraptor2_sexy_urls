package org.vraptor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a method as a logic.
 * 
 * This is not required, since all all public
 * methods of a component are already considered
 * bussiness logic by VRaptor.
 * 
 * @author Guilherme Silveira
 */
@Target(ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Logic {
	
	/**
	 * The name of the form parameters that will be parsed
	 * from the request to be injected as this logic arguments.
	 * 
	 * If not present, VRaptor will use the argument class name decapitalized
	 * when searching in the request parameters.
	 * 
	 * <code>
	 * void method(SomeClass argument, OtherClass argument2)
	 * </code>
	 * 
	 * will look fo servlet request parameters "someClass"
	 * and "otherClass" repectively.  Or you can name them
	 * using this annotation attribute.
	 *  
	 * The array must have length equals to the number of arguments
	 * that this logic method receives.
	 * 
	 */
	public String[] parameters() default {};
	
	/**
	 * Logic names. A Logic can have more than one name, creating
	 * nicknames for the same logic.
	 * 
	 * @return names
	 */
	public String[] value() default {};
}
