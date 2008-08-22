package org.vraptor.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Represents a field annotated.
 * 
 * @author Guilherme Silveira
 * @param <T>
 *            the type of annotation which shall be used
 */
public class FieldAnnotation<T extends Annotation> {

	private final T annotation;

	private final Field field;

	public FieldAnnotation(T annotation, Field field) {
		this.annotation = annotation;
		this.field = field;
	}

	/**
	 * @return Returns the annotation.
	 */
	public T getAnnotation() {
		return this.annotation;
	}

	/**
	 * @return Returns the field.
	 */
	public Field getField() {
		return this.field;
	}

}
