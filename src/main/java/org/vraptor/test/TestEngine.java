package org.vraptor.test;

import javax.servlet.ServletContext;

import org.vraptor.config.ConfigException;
import org.vraptor.core.Controller;
import org.vraptor.core.ControllerFactory;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.scope.DefaultApplicationContext;

/**
 * A test engine.
 * 
 * @author Guilherme Silveira
 * 
 */
public class TestEngine {

	private final Controller controller;

	private final ApplicationContext applicationContext;

	/**
	 * Creates a new test engine.
	 * 
	 * @throws TestConfigException
	 */
	private TestEngine() throws TestConfigException {
		this(new MockedServletContext());
	}

	public TestEngine(ServletContext context) {
		this.applicationContext = new DefaultApplicationContext(context);
		try {
			this.controller = new ControllerFactory().configure(context);
		} catch (ConfigException e) {
			throw new TestConfigException(e);
		}
	}

	/**
	 * Loads vraptor configuration files and setups the engine
	 * 
	 * @return the new engine
	 * @throws ConfigException
	 *             some configuration problem was found
	 */
	public static TestEngine createEngine() throws TestConfigException {
		return new TestEngine();
	}

	public static TestEngine createEngine(ServletContext context) throws TestConfigException {
		return new TestEngine(context);
	}

	/**
	 * Creates a new test session
	 * 
	 * @return a test session
	 */
	public TestSession createSession() {
		return new DefaultTestSession(controller);
	}

	/**
	 * Returns the application context
	 * 
	 * @return the context
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
