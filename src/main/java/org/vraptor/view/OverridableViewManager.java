package org.vraptor.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.vraptor.LogicRequest;
import org.vraptor.introspector.ExpressionEvaluationException;
import org.vraptor.introspector.ExpressionEvaluator;

/**
 * Overriden view manager checks if the view was overriden and returns it.
 * Otherwise it returns the default view that its internal view manager would
 * return.
 * 
 * @author Guilherme Silveira
 */
public class OverridableViewManager implements ViewManager {

	private static final Logger LOG = Logger.getLogger(OverridableViewManager.class);

	private static final String REDIRECT = "redirect:";

	private final ExpressionEvaluator evaluator = new ExpressionEvaluator();

	private final ViewManager internalManager;

	private final Map<String, String> views = new HashMap<String, String>();

	/**
	 * Creates the overriden view manager with a default view manager
	 * 
	 * @param viewManager
	 *            the default view manager
	 */
	public OverridableViewManager(ViewManager viewManager) {
		this.internalManager = viewManager;
	}

	public void directForward(LogicRequest logicRequest, String result, String forwardUrl) throws ViewException {
		internalManager.directForward(logicRequest, result, forwardUrl);
	}

	public void forward(LogicRequest logicRequest, String result) throws ViewException {
		// if it is overriden, overrides the forward

		String key = logicRequest.getRequestInfo().getComponentName() + "."
				+ logicRequest.getRequestInfo().getLogicName() + "." + result;
		try {
			result = evaluator.parseExpression(result, logicRequest);
		} catch (ExpressionEvaluationException e) {
			throw new ViewException(e);
		}
		if (this.views.containsKey(key)) {
			String forward = this.views.get(key);
			if (LOG.isDebugEnabled()) {
				LOG.debug("overriden view found " + key + " : " + forward);
			}

			if (forward.startsWith(REDIRECT)) {
				redirect(logicRequest, forward.substring(REDIRECT.length()));
			} else {
				internalManager.directForward(logicRequest, result, forward);
			}
		} else {
			internalManager.forward(logicRequest, result);
		}
	}

	/**
	 * Register a new view
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void overrideView(String key, String value) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("Overriding view %s --> %s", key, value));
		}
		this.views.put(key, value);
	}

	public void redirect(LogicRequest request, String result) throws ViewException {
		try {
			internalManager.redirect(request, evaluator.parseExpression(result, request));
		} catch (ExpressionEvaluationException e) {
			throw new ViewException(e);
		}
	}

}
