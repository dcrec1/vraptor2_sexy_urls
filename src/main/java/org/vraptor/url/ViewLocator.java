package org.vraptor.url;

import javax.servlet.http.HttpServletRequest;

import org.vraptor.LogicException;
import org.vraptor.component.LogicMethod;
import org.vraptor.view.ViewManager;

/**
 * Locates the view manager for this request.
 * 
 * @author Guilherme Silveira
 */
public interface ViewLocator {

	/**
	 * Returns the selected view manager for this request.
	 * @throws LogicException 
	 */
	ViewManager locate(HttpServletRequest req, LogicMethod method, ViewManager defaultViewManager) throws InvalidURLException, LogicException;

}
