package org.vraptor.component;

import java.util.ArrayList;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.Logic;
import org.vraptor.annotations.Viewless;
import org.vraptor.validator.UnstableValidationException;

public class DefaultLogicMethodTest extends AbstractTest {

	public void testGetsItsName() throws SecurityException, NoSuchMethodException, LogicException {
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(), "string", LogicTest.class.getMethod("execute"), null,new ArrayList<MethodParameter>());
		assertEquals("string", method.getName());
	}

	public void testReturnsAnOkString() throws SecurityException, NoSuchMethodException, LogicException {
		LogicTest test = new LogicTest();
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"string", LogicTest.class.getMethod("execute"), null,new ArrayList<MethodParameter>());
		String value = method.execute(test, createLogicRequest(), new Object[0]);
		assertEquals("ok", value);
	}

	public void testReturnsVoidMeansOk() throws SecurityException, NoSuchMethodException, LogicException {
		LogicTest test = new LogicTest();
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"void", LogicTest.class.getMethod("executeVoid"), null,new ArrayList<MethodParameter>());
		Object value = method.execute(test, createLogicRequest(), new Object[0]);
		assertEquals("ok", value);
	}

	public void testFindsAViewlessMethod() throws SecurityException, NoSuchMethodException, LogicException {
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"void", LogicTest.class.getMethod("viewless"), null,new ArrayList<MethodParameter>());
		assertFalse(method.shouldRedirect());
	}

	public void testFindsAMethodWithViewRedirection() throws SecurityException, NoSuchMethodException, LogicException {
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"void", LogicTest.class.getMethod("executeVoid"), null,new ArrayList<MethodParameter>());
		assertTrue(method.shouldRedirect());
	}

	public void testException() throws SecurityException, NoSuchMethodException, LogicException {
		LogicTest test = new LogicTest();
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"problem", LogicTest.class.getMethod("executeProblem"), null,new ArrayList<MethodParameter>());
		try {
			method.execute(test, createLogicRequest(), new Object[0]);
			fail();
		} catch (LogicException e) {
			// ok
		}
	}

	public void testExecutesValidationWithException() throws SecurityException, NoSuchMethodException, LogicException {
		LogicTest test = new LogicTest();
		DefaultLogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"withValidation", LogicTest.class
				.getMethod("withValidation"), LogicTest.class.getMethod("validateWithValidation"), new ArrayList<MethodParameter>());
		try {
			method.validate(test, createLogicRequest(), null, new Object[] {});
			fail();
		} catch (UnstableValidationException e) {
			// ok
		}
	}

	public void testExecutesMethodWithException() throws Throwable {
		LogicTest test = new LogicTest();
		LogicMethod method = new DefaultLogicMethod(registry.getValidationErrorsFactory(),"problem", LogicTest.class.getMethod("executeProblem"), null, new ArrayList<MethodParameter>());
		try {
			method.execute(test, createLogicRequest(), new Object[0]);
			fail();
		} catch (LogicException e) {
			assertEquals(e.getCause().getClass(), ArithmeticException.class);
		}
	}

	public class LogicTest {
		@Logic("string")
		public String execute() {
			return "ok";
		}

		@Viewless
		public void viewless() {
		}

		@Logic("void")
		public void executeVoid() {
		}

		@Logic("problem")
		public void executeProblem() {
			@SuppressWarnings("unused")
			int i = 1 / 0;
		}

		public void withValidation() {

		}

		public void validateWithValidation() {
			throw new RuntimeException();
		}
	}


}
