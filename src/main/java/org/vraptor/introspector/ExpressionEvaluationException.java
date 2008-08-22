package org.vraptor.introspector;

import org.vraptor.VRaptorException;

public class ExpressionEvaluationException extends VRaptorException {

	private static final long serialVersionUID = -4065456374759098252L;

	public ExpressionEvaluationException(String msg) {
		super(msg);
	}

	public ExpressionEvaluationException(String msg, Exception e) {
		super(msg, e);
	}

}
