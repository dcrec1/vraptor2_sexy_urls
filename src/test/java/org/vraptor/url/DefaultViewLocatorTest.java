package org.vraptor.url;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.LogicException;
import org.vraptor.annotations.Remotable;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.test.MockedRequest;
import org.vraptor.view.RemoteViewManager;
import org.vraptor.view.ViewManager;

/**
 * Tests the default url dealer.
 * 
 * @author Guilherme Silveira
 */
public class DefaultViewLocatorTest extends AbstractTest {

	private ViewLocator manager;
	private Mock mockedViewManager;
	private ViewManager viewManager;
	private LogicMethod remotableMethod;
	
	public static class MyType {
		@Remotable
		public void method() {
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockedViewManager = mock(ViewManager.class);
		remotableMethod = createComponentType(MyType.class).getLogic("method");
		viewManager = (ViewManager) mockedViewManager.proxy();
		manager = new DefaultViewLocator();
	}

	public void testFindsJSONURLViewType() throws InvalidURLException, LogicNotFoundException, ComponentNotFoundException, LogicException {
		assertEquals(RemoteViewManager.class, locateViewManager("/a/b", "ajax").getClass());
		mockedViewManager.verify();
	}

	public void testFindsXMLURLViewType() throws InvalidURLException, LogicNotFoundException, ComponentNotFoundException, LogicException {
		assertEquals(RemoteViewManager.class, locateViewManager("/a/b", "xml").getClass());
		mockedViewManager.verify();
	}

	public void testDoesntFindURLViewType() throws InvalidURLException, LogicNotFoundException, ComponentNotFoundException, LogicException {
		assertEquals(viewManager, locateViewManager("/a/b", "text/htm"));
		mockedViewManager.verify();
	}

	public void testFindsNullURLViewType() throws InvalidURLException, LogicNotFoundException, ComponentNotFoundException, LogicException {
		assertEquals(viewManager, locateViewManager("/a/b", "text/htm"));
		mockedViewManager.verify();
	}

	private ViewManager locateViewManager(String uri, String acceptedTypes) throws InvalidURLException, LogicNotFoundException, ComponentNotFoundException, LogicException {
		MockedRequest req = new MockedRequest(uri, createSession());
		req.addHeader("Accept", acceptedTypes);
		return manager.locate(req, remotableMethod, viewManager);
	}

}
