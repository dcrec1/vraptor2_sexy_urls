package org.vraptor.component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.vraptor.AbstractTest;
import org.vraptor.annotations.Logic;
import org.vraptor.annotations.Parameter;

public class DefaultParameterInfoProviderTest extends AbstractTest {

	private DefaultParameterInfoProvider provider;

	protected void setUp() throws Exception {
		super.setUp();
		provider = new DefaultParameterInfoProvider();
	}

	public void testReadsMoreThanOneParameter() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"twoParams"));
		assertEquals(2, params.size());
	}

	public void testReadsNoParameter() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"noParams"));
		assertEquals(0, params.size());
	}

	public void testReadsATypeParam() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"twoParams"));
		assertEquals("date", params.get(0).getKey());
	}

	public void testReadsAPrimitiveParam() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"primitiveParam"));
		assertEquals("int", params.get(0).getKey());
	}

	private Method loadMethod(Class<?> type, String methodName) throws InvalidComponentException {
		for (Method method : type.getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		fail("Method " + methodName + " not found.");
		return null;
	}


	public void testAnnotatedParam() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"annotatedParam"));
		assertEquals("_date_", params.get(0).getKey());
	}

	public void testAnnotatedLogicWithParameterNames() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"annotatedWithLogic"));
		assertEquals("_date_", params.get(0).getKey());
		assertEquals("_other_date_", params.get(1).getKey());
	}

	public void testAnnotatedLogicWithParameterNames2() throws InvalidComponentException {
		List<MethodParameter> params = provider.provideFor(loadMethod(Params.class,"annotatedWithLogicAndParameter"));
		assertEquals("_date_", params.get(0).getKey());
		assertEquals("_other_date_", params.get(1).getKey());
	}

	public void testAnnotatedLogicWithParameterNamesButWrongNumberOfNames() throws InvalidComponentException {
		try {
			provider.provideFor(loadMethod(Params.class,"annotatedWithLogicButWrongNumberOfNames"));
			fail();
		} catch (IllegalArgumentException e) {

		}
	}


	public static class Params {
		
		public void noParams() {
		}
		
		public void primitiveParam(int p) {
		}
		
		public void typeParam(Date d) {
		}
		
		public void twoParams(Date d, String p) {
		}

		// parameter and useless annotation
		public void annotatedParam(@Parameter(key = "_date_")
		@Deprecated
		Date d) {

		}

		@Logic(parameters = { "_date_", "_other_date_" })
		public void annotatedWithLogic(Date d, Date d2) {

		}

		@Logic(parameters = { "_date_", "_other_date_" })
		public void annotatedWithLogicAndParameter(Date d, @Parameter
		Date d2) {

		}

		@Logic(parameters = { "_date_", "_other_date_" })
		public void annotatedWithLogicAndParameterOverriding(Date d, @Parameter(key = "x")
		Date d2) {

		}

		@Logic(parameters = { "_date_", "_other_date_", "_non_existant_" })
		public void annotatedWithLogicButWrongNumberOfNames(Date d, Date d2) {

		}
	}

}
