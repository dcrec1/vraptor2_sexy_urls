package org.vraptor.view;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;

/**
 * Regex based view manager.
 * 
 * @author Guilherme Silveira
 */
public class RegexViewManager implements ViewManager {

	private static final Logger LOG = Logger.getLogger(RegexViewManager.class);

	private final String replacement;

	private final String regex = ("(.*);(.*);(.*)");

	/**
	 * Instantiates the regex view manager with this replacement string.
	 * 
	 * @param replacement
	 *            the replacement string
	 */
	public RegexViewManager(String replacement) {

		this.replacement = translateExpression(replacement);
	}

	/**
	 * Method replaces any $component with $1, $logic with $2 and $result with
	 * $3 within the string.
	 * 
	 * @param replacement
	 *            the string to replace
	 * @return the translated string
	 */
	String translateExpression(String replacement) {
		if (replacement != null && replacement.matches(".*(\\$component|\\$logic|\\$result).*")) {
			replacement = replacement.replaceAll("\\$component", "\\$1").replaceAll("\\$logic", "\\$2").replaceAll(
					"\\$result", "\\$3");
		}
		return replacement;
	}

	/**
	 * 
	 * @see org.vraptor.view.ViewManager#getForward(org.vraptor.LogicRequest,
	 *      java.lang.String)
	 */
	private String getForward(LogicRequest request, String result) throws ViewException {
		String value = request.getRequestInfo().getComponentName() + ";" + request.getRequestInfo().getLogicName()
				+ ";" + result;
		return value.replaceAll(regex, replacement);

	}

	public void forward(LogicRequest logicRequest, String result) throws ViewException {
		String forward = getForward(logicRequest, result);
		directForward(logicRequest, result, forward);
	}

	public void directForward(LogicRequest logicRequest, String result, String forwardUrl) throws ViewException {
		LOG.debug("Server-side redirect to: " + forwardUrl);
		try {
			logicRequest.getRequest().getRequestDispatcher(forwardUrl).forward(logicRequest.getRequest(),
					logicRequest.getResponse());
		} catch (ServletException ex) {
			if (ex.getCause() != null) {
				throw new ViewException(ex.getCause());
			}
			throw new ViewException(ex);
		} catch (IOException ex) {
			throw new ViewException(ex);
		}
	}

	public void redirect(LogicRequest logicRequest, String url) throws ViewException {
		LOG.debug("Client-side redirect to: " + url);
		try {
			logicRequest.getResponse().sendRedirect(url);
		} catch (IOException e) {
			throw new ViewException(e);
		}
	}

}
