package org.vraptor.config;

import org.vraptor.VRaptorException;

/**
 * Something bad happenned during configuration
 * 
 * @author Guilherme Silveira
 */
public class ConfigException extends VRaptorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5267635588148377816L;

	/**
	 * 
	 */
	public ConfigException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConfigException(Throwable cause) {
		super(cause);
	}

}
