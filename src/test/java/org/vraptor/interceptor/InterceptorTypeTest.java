package org.vraptor.interceptor;

import junit.framework.TestCase;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.view.ViewException;

/**
 * @author Guilherme Silveira
 */
public class InterceptorTypeTest extends TestCase {

	public void testCachesProducts() throws ComponentInstantiationException {
		InterceptorType type1 = InterceptorType.getType(SimpleInterceptor.class);
		InterceptorType type2 = InterceptorType.getType(SimpleInterceptor.class);
		assertEquals(type1, type2);
	}

	public static class SimpleInterceptor implements Interceptor {
		public void intercept(LogicFlow logic) throws LogicException, ViewException {
		}
	}

	public static class OtherInterceptor implements Interceptor {
		public void intercept(LogicFlow logic) throws LogicException, ViewException {
		}
	}

	public void testLoadsEmptyInterceptorList() {
		assertEquals(0, InterceptorType.getInterceptors(EmptyList.class).size());
	}

	public void testSimpleGetInterceptorsList() {
		assertEquals("Did not register the interceptor", 1, InterceptorType.getInterceptors(Single.class).size());
		assertEquals(SimpleInterceptor.class, InterceptorType.getInterceptors(Double.class).get(0)
				.getInterceptorClass());
	}

	public void testTwoGetInterceptorsList() {
		assertEquals("Did not register the interceptors", 2, InterceptorType.getInterceptors(Double.class).size());
		assertEquals(SimpleInterceptor.class, InterceptorType.getInterceptors(Double.class).get(0)
				.getInterceptorClass());
		assertEquals(OtherInterceptor.class, InterceptorType.getInterceptors(Double.class).get(1).getInterceptorClass());
	}

	@InterceptedBy(SimpleInterceptor.class)
	class Single {
	}

	@InterceptedBy( { SimpleInterceptor.class, OtherInterceptor.class })
	class Double {
	}

	class EmptyList {
	}

	public static class WrongInterceptor implements Interceptor {
		public WrongInterceptor() {
			throw new RuntimeException();
		}
		public void intercept(LogicFlow logic) throws LogicException, ViewException {
		}
	}

}
