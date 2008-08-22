package org.vraptor.test;

/**
 * A test configuration problem.
 * 
 * @author Guilherme Silveira
 * 
 */
public class TestConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3672106177869843508L;

	public TestConfigException() {
		super();
	}

	public TestConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestConfigException(String message) {
		super(message);
	}

	public TestConfigException(Throwable cause) {
		super(cause);
	}

}
