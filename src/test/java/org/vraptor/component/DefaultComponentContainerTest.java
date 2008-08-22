package org.vraptor.component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.ValidationErrorsFactory;
import org.vraptor.test.MockedServletContext;
import org.vraptor.url.InvalidURLException;
import org.vraptor.url.LogicLocator;
import org.vraptor.url.ViewLocator;
import org.vraptor.validator.ValidationErrors;
import org.vraptor.view.ViewManager;

public class DefaultComponentContainerTest extends AbstractTest {
	
	public static class MyManager implements LogicLocator{
		
		public MyManager(ServletContext context) {
			
		}

		public LogicMethod locate(HttpServletRequest req)
				throws InvalidURLException {
			return null;
		}
		
	}

	public void testServletContextGetsRegisteredAndAccessed() {
		MockedServletContext context = createServletContext();
		context.setInitParameter(LogicLocator.class.getName(), MyManager.class.getName());
		DefaultComponentContainer container = new DefaultComponentContainer(context);
		assertEquals(MyManager.class, container.getLogicLocator().getClass());
	}
	
	public static class MyValidationErrorsFactory implements ValidationErrorsFactory {
		public ValidationErrors newInstance() {
			return null;
		}
	}

	public void testValidationErrorsFactoryGetsRegisteredAndAccessed() {
		MockedServletContext context = createServletContext();
		context.setInitParameter(ValidationErrorsFactory.class.getName(), MyValidationErrorsFactory.class.getName());
		DefaultComponentContainer container = new DefaultComponentContainer(context);
		assertEquals(MyValidationErrorsFactory.class, container.getValidationErrorsFactory().getClass());
	}


	public void testViewLocatorGetsRegisteredAndAccessed() {
		MockedServletContext context = createServletContext();
		context.setInitParameter(ViewLocator.class.getName(), MyViewLocator.class.getName());
		DefaultComponentContainer container = new DefaultComponentContainer(context);
		assertEquals(MyViewLocator.class, container.getViewLocator().getClass());
	}
	public static class MyViewLocator implements ViewLocator {
		public ViewManager locate(HttpServletRequest req, LogicMethod method,
				ViewManager defaultViewManager) throws InvalidURLException,
				LogicException {
			return null;
		}
	}

}
