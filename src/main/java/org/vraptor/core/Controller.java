package org.vraptor.core;

import org.vraptor.LogicException;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.introspector.Introspector;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;
import org.vraptor.webapp.WebApplication;

/**
 * A basic vraptor controller.
 *
 * @author Guilherme Silveira
 * @since 2.4
 */
public interface Controller {

	/**
	 * @return the application
	 */
	WebApplication getWebApplication();

	String execute(WebRequest request) throws InvalidURLException, ComponentNotFoundException, LogicNotFoundException,
			ViewException, InterceptorInstantiationException, LogicException, StaticResourceException;

	Introspector getIntrospector();

}