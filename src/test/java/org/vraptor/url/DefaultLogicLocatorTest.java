package org.vraptor.url;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.vraptor.AbstractTest;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.StaticResourceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Tests the default url dealer.
 * 
 * @author Guilherme Silveira
 */
public class DefaultLogicLocatorTest extends AbstractTest {

	private Mockery mockery;
	private HttpServletRequest request;
	private ComponentManager manager;
	private DefaultLogicLocator locator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockery = new Mockery();
		request = mockery.mock(HttpServletRequest.class);
		locator = new DefaultLogicLocator(new FakeManager());
	}

	private void useURL(final String contextPath, final String uri) {
		mockery.checking(new Expectations() {
			{
				one(request).getContextPath();
				will(returnValue(contextPath));
				one(request).getRequestURI();
				will(returnValue(uri));
				atLeast(1).of(request).getAttribute(
						"javax.servlet.include.request_uri");
				will(returnValue(null));
			}
		});
	}
	
	public void testWelcomeFile() throws Exception {
		try {
			useURL("", "");
			locator.locate(request);
			fail();
		} catch (StaticResourceException e) {
			// ok
		}
	}

	public void testChecksSimpleUrl() throws Exception {
		useURL("/context", "/context/component/action");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("action", logicMethod.getName());
		assertEquals("component", logicMethod.getComponentType().getName());
		mockery.assertIsSatisfied();
	}

	public void testRootContextPath() throws LogicNotFoundException,
			ComponentNotFoundException, InvalidURLException, StaticResourceException {
		useURL("", "/component/action");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("action", logicMethod.getName());
		assertEquals("component", logicMethod.getComponentType().getName());
		mockery.assertIsSatisfied();
	}

	public void testRootContextPathWithNamespaces()
			throws LogicNotFoundException, ComponentNotFoundException,
			InvalidURLException, StaticResourceException {
		useURL("", "/module/component/action");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("action", logicMethod.getName());
		assertEquals("module/component", logicMethod.getComponentType()
				.getName());
		mockery.assertIsSatisfied();
	}

	public void testChecksUrlWithComponentModule() throws InvalidURLException,
			LogicNotFoundException, ComponentNotFoundException, StaticResourceException {
		useURL("/context", "/context/module/component/action");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("action", logicMethod.getName());
		assertEquals("module/component", logicMethod.getComponentType()
				.getName());
		mockery.assertIsSatisfied();
	}

	public void testChecksUrlWithComplexComponentNamespace()
			throws InvalidURLException, LogicNotFoundException,
			ComponentNotFoundException, StaticResourceException {
		useURL("/context", "/context/super/complex/module/component/action");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("action", logicMethod.getName());
		assertEquals("super/complex/module/component", logicMethod
				.getComponentType().getName());
		mockery.assertIsSatisfied();
	}

	public void testChecksDifferentExtensionUrl() throws InvalidURLException,
			LogicNotFoundException, ComponentNotFoundException, StaticResourceException {
		try {
			useURL("/context", "/context/component/action.weird");
			locator.locate(request);
			fail();
		} catch (StaticResourceException e) {
			// ok
		}
	}

	public void testChecksUrlMissingLogicName() throws LogicNotFoundException,
			ComponentNotFoundException, InvalidURLException, StaticResourceException {
		useURL("/context", "/context/component/");
		LogicMethod logicMethod = locator.locate(request);

		assertEquals("index", logicMethod.getName());
		assertEquals("component", logicMethod.getComponentType().getName());
		mockery.assertIsSatisfied();
	}

	public void testChecksUrlMissingLogicNameAndExtension()
			throws LogicNotFoundException, ComponentNotFoundException, StaticResourceException {
		try {
			useURL("/context", "/context/component");
			locator.locate(request);
			fail();
		} catch (InvalidURLException e) {
			// ok
		}
	}

	public void testEncapsulatedURIForServerSideRequests()
			throws InvalidURLException, LogicNotFoundException,
			ComponentNotFoundException, StaticResourceException {
		mockery.checking(new Expectations() {
			{
				one(request).getContextPath();
				will(returnValue("/context"));
				never(request).getRequestURI();

				atLeast(1).of(request).getAttribute(
						"javax.servlet.include.request_uri");
				will(returnValue("xpto.com/context/module/component/method"));
			}
		});
		LogicMethod logicMethod = locator.locate(request);
		assertEquals("method", logicMethod.getName());
		assertEquals("module/component", logicMethod.getComponentType()
				.getName());
		mockery.assertIsSatisfied();
	}

	private class FakeManager implements ComponentManager {

		public ComponentType getComponent(final String name,
				final String logicName) throws ComponentNotFoundException,
				LogicNotFoundException {
			final ComponentType componentType = mockery
					.mock(ComponentType.class);
			final LogicMethod logicMethod = mockery.mock(LogicMethod.class);
			mockery.checking(new Expectations() {
				{
					allowing(componentType).getName();
					will(returnValue(name));
					allowing(logicMethod).getName();
					will(returnValue(logicName));
					allowing(componentType).getLogic(logicName);
					will(returnValue(logicMethod));
					allowing(logicMethod).getComponentType();
					will(returnValue(componentType));
				}
			});
			return componentType;
		}

		public boolean register(String type) throws InvalidComponentException {
			return false; // To change body of implemented methods use File |
							// Settings | File Templates.
		}

		public Set<ComponentType> getComponents() {
			return null; // To change body of implemented methods use File |
							// Settings | File Templates.
		}

		public void register(ComponentType type) throws LogicNotFoundException {
		}

		public ComponentType getComponentType(Class<?> type)
				throws InvalidComponentException {
			return null; // To change body of implemented methods use File |
							// Settings | File Templates.
		}
	}

}
