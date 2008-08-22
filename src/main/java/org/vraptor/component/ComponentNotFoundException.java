package org.vraptor.component;

import org.vraptor.VRaptorException;

/**
 * Component not found.
 * 
 * @author Guilherme Silveira
 */
public class ComponentNotFoundException extends VRaptorException {

	private static final long serialVersionUID = 7028857766134809928L;

	public ComponentNotFoundException(String message) {
		super(message);
	}

    public ComponentNotFoundException(Throwable cause) {
        super(cause);
    }

    public ComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
