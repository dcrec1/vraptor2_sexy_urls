package org.vraptor.view;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.annotations.Out;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.core.DefaultLogicDefinition;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.scope.ScopeType;
import org.vraptor.test.MockedHttpSession;
import org.vraptor.test.MockedRequest;
import org.vraptor.test.MockedResponse;
import org.vraptor.test.MockedServletContext;

public class RemoteViewManagerTest extends AbstractTest {

	private Mock mock;

	private ViewManager manager;

	private MockedRequest request;

	private MockedResponse response;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.mock = mock(ViewManager.class);
		this.manager = (ViewManager) mock.proxy();
		this.request = new MockedRequest("", new MockedHttpSession(new MockedServletContext()));
		this.response = new MockedResponse();
	}

	public static class TestRemotable {
		@SuppressWarnings("unused")
		@Out
		private String out = "testMe";

		@Remotable(depth = 1)
		public void test() {
		}
	}

	public static class TestRemotableWhichOutjectsToSession {
		@SuppressWarnings("unused")
		@Out(scope = ScopeType.SESSION)
		private String out = "testMe";

		@Remotable(depth = 1)
		public void test() {
		}
	}

	public static class TestRemotableWhichOutjectsToApplicationContext {
		@SuppressWarnings("unused")
		@Out(scope = ScopeType.APPLICATION)
		private String out = "testMe";

		@Remotable(depth = 1)
		public void test() {
		}
	}

	public void testAjaxViewType() throws ViewException, SecurityException, NoSuchMethodException,
			InvalidComponentException {

		ComponentType type = createComponentType(TestRemotable.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotable(), type,
				createLogicMethod(TestRemotable.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.AJAX);
		remote.forward(logic, "");

		assertEquals("application/json", response.getContentType());
		assertEquals("{\"out\":\"testMe\"}", response.getOutput().getBuffer().toString());
	}

	public void testAjaxViewTypeWithOutjectionToSessionContext() throws ViewException, SecurityException,
			NoSuchMethodException, InvalidComponentException {

		ComponentType type = createComponentType(TestRemotableWhichOutjectsToSession.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotableWhichOutjectsToSession(), type,
				createLogicMethod(TestRemotableWhichOutjectsToSession.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.AJAX);
		remote.forward(logic, "");

		assertEquals("application/json", response.getContentType());
		assertEquals("{\"out\":\"testMe\"}", response.getOutput().getBuffer().toString());
	}

	public void testAjaxViewTypeWithOutjectionToApplicationContext() throws ViewException, SecurityException,
			NoSuchMethodException, InvalidComponentException {

		ComponentType type = createComponentType(TestRemotableWhichOutjectsToApplicationContext.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotableWhichOutjectsToApplicationContext(), type,
				createLogicMethod(TestRemotableWhichOutjectsToApplicationContext.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.AJAX);
		remote.forward(logic, "");

		assertEquals("application/json", response.getContentType());
		assertEquals("{\"out\":\"testMe\"}", response.getOutput().getBuffer().toString());
	}

	public void testXMLViewType() throws ViewException, SecurityException, NoSuchMethodException,
			InvalidComponentException {

		ComponentType type = createComponentType(TestRemotable.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotable(), type,
				createLogicMethod(TestRemotable.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.XML);
		remote.forward(logic, "");

		assertEquals("text/xml", response.getContentType());
		assertTrue(response.getOutput().getBuffer().toString().contains("out"));
		assertTrue(response.getOutput().getBuffer().toString().contains("testMe"));
	}

	public void testXMLViewTypeWithOutjectionToSessionContext() throws ViewException, SecurityException,
			NoSuchMethodException, InvalidComponentException {

		ComponentType type = createComponentType(TestRemotableWhichOutjectsToSession.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotableWhichOutjectsToSession(), type,
				createLogicMethod(TestRemotableWhichOutjectsToSession.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.XML);
		remote.forward(logic, "");

		assertEquals("text/xml", response.getContentType());
		assertTrue(response.getOutput().getBuffer().toString().contains("out"));
		assertTrue(response.getOutput().getBuffer().toString().contains("testMe"));
	}

	public void testXMLViewTypeWithOutjectionToApplicationContext() throws ViewException, SecurityException,
			NoSuchMethodException, InvalidComponentException {

		ComponentType type = createComponentType(TestRemotableWhichOutjectsToApplicationContext.class);
		DefaultLogicRequest logic = createLogicRequest("component", "logic", request, response,
				createServletContext());

		logic.setLogicDefinition(new DefaultLogicDefinition(new TestRemotableWhichOutjectsToApplicationContext(), type,
				createLogicMethod(TestRemotableWhichOutjectsToApplicationContext.class.getMethod("test"))));

		RemoteViewManager remote = new RemoteViewManager(manager, RemoteView.XML);
		remote.forward(logic, "");

		assertEquals("text/xml", response.getContentType());
		assertTrue(response.getOutput().getBuffer().toString().contains("out"));
		assertTrue(response.getOutput().getBuffer().toString().contains("testMe"));
	}

}
