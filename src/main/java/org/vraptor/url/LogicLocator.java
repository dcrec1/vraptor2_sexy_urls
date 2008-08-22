package org.vraptor.url;

import javax.servlet.http.HttpServletRequest;

import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;

/**
 * Locates the logic method for this request.
 * 
 * @author Guilherme Silveira
 */
public interface LogicLocator {

	/**
	 * Returns the selected logic method for this request.
	 * 
	 * @throws LogicNotFoundException
	 * @throws ComponentNotFoundException
	 * @throws StaticResourceException
	 */
	LogicMethod locate(HttpServletRequest req) throws InvalidURLException,
			LogicNotFoundException, ComponentNotFoundException,
			StaticResourceException;

}
