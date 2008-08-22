package org.vraptor.component;

import org.vraptor.VRaptorException;

/**
 * Unable to instantiate a component.
 * 
 * @author Guilherme Silveira
 */
public class ComponentInstantiationException extends VRaptorException {

	private static final long serialVersionUID = 4168415032854027777L;

	public ComponentInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponentInstantiationException(String message) {
		super(message);
	}

}
