package org.vraptor.introspector;

import org.vraptor.annotations.In;
import org.vraptor.annotations.Parameter;
import org.vraptor.annotations.Read;
import org.vraptor.component.FieldAnnotation;

/**
 * Responsible for extracting keys from field annotations
 * @deprecated move to the field annotations themselves
 * @author Guilherme Silveira
 */
@Deprecated
public class KeyExtractor {

	/**
	 * Retrieves the key value for an in annotation
	 * 
	 * @param info
	 *            the annotation
	 * @return the key value
	 */
	String extractInKey(FieldAnnotation<In> info) {
		String key = info.getAnnotation().key();
		if ("".equals(key)) {
			key = info.getField().getName();
		}
		return key;
	}

	/**
	 * Retrieves the key value for an in annotation
	 * 
	 * @param info
	 *            the annotation
	 * @return the key value
	 */
	String extractReadKey(FieldAnnotation<Read> info) {
		String key = info.getAnnotation().key();
		if ("".equals(key)) {
			key = info.getField().getName();
		}
		return key;
	}

	String extractParamKey(FieldAnnotation<Parameter> info) {
		String key = info.getAnnotation().key();
		if ("".equals(key)) {
			key = info.getField().getName();
		}
		return key;
	}

}
