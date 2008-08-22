package org.vraptor.url;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.vraptor.LogicException;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.LogicMethod;
import org.vraptor.view.RemoteView;
import org.vraptor.view.RemoteViewManager;
import org.vraptor.view.ViewManager;

public class DefaultViewLocator implements ViewLocator {

	private static final Logger LOG = Logger
			.getLogger(DefaultViewLocator.class);

	private static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";

	public ViewManager locate(HttpServletRequest req, LogicMethod method,
			ViewManager defaultViewManager) throws InvalidURLException,
			LogicException {

		String viewType = req.getHeader("Accept");

		// TODO allow view managers to be expanded by registering your own
		// managers...
		// this code should look go for each one as in the converter finder
		if (("xml".equals(viewType) || "ajax".equals(viewType))) {
			if (!method.getMetadata().isAnnotationPresent(Remotable.class)) {
				throw new LogicException("logic method is not @Remotable");
			}
			return new RemoteViewManager(defaultViewManager, RemoteView
					.valueOf(viewType.toUpperCase()));
		}

		return defaultViewManager;
	}

	private String extractURI(HttpServletRequest req) {
		String uri;
		// details in :
		// http://www.caucho.com/resin-3.0/webapp/faq.xtp#forward-path
		// and
		// http://www.javaworld.com/javaworld/jw-03-2003/jw-0328-servlet.html?page=4
		if (req.getAttribute(INCLUDE_REQUEST_URI) != null) {
			uri = (String) req.getAttribute(INCLUDE_REQUEST_URI);
		} else {
			uri = req.getRequestURI();
		}

		return uri;
	}

}
