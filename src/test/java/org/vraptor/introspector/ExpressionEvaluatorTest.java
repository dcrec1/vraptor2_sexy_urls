package org.vraptor.introspector;

import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;

public class ExpressionEvaluatorTest extends AbstractTest {

	public void testEvalsEmptyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("", request);
		assertEquals("", result);
	}

	public void testEvalsEmptyLanguageExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("${}", request);
		assertEquals("", result);
	}

	public void testEvalsStringExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("test", request);
		assertEquals("test", result);
	}

	public void testEvalsSimplePropertyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", "val");
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("${obj}", request);
		assertEquals("val", result);
	}

	public void testEvalsMixedSimplePropertyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", "val");
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("text=${obj}", request);
		assertEquals("text=val", result);
	}

	public static class Five {
		int val = 5;
		Integer nulled;

		public int getVal() {
			return val;
		}

		public Integer getNulled() {
			return nulled;
		}
	}

	public void testEvalsPropertyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new Five());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("${obj.val}", request);
		assertEquals("5", result);
	}

	public void testEvalsMixedPropertyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new Five());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("test=${obj.val}", request);
		assertEquals("test=5", result);
	}

	public void testSequenceMixedPropertyExpression() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new Five());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("test=${obj.val}${obj.val}", request);
		assertEquals("test=55", result);
	}

	public static class FiveException {
		public int getVal() throws Exception {
			throw new Exception("nasty");
		}
	}

	public void testEvaluatesExpressionWithGetterException() {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new FiveException());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		try {
			eval.parseExpression("test=${obj.val}${obj.val}", request);
			fail();
		} catch (ExpressionEvaluationException e) {
			// ok
		}
	}

	public void testEvaluatesNotWellWrittenExpression() {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new FiveException());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		try {
			eval.parseExpression("test=$a{obj.val}${obj.val}", request);
			fail();
		} catch (ExpressionEvaluationException e) {
			// ok
		}
	}

	public void testEvaluatesExpressionInsideExpression() {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new FiveException());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		try {
			eval.parseExpression("test=${${obj.val}}", request);
			fail();
		} catch (ExpressionEvaluationException e) {
			// ok
		}
	}

	public void testEvalsSupportsNullAtTheEnd() throws ExpressionEvaluationException {
		LogicRequest request = createLogicRequest();
		request.getRequestContext().setAttribute("obj", new Five());
		ExpressionEvaluator eval = new ExpressionEvaluator();
		String result = eval.parseExpression("test=${obj.nulled}", request);
		assertEquals("test=null", result);
	}

}
