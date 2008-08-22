package org.vraptor.view;

import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.vraptor.AbstractTest;
import org.vraptor.LogicRequest;
import org.vraptor.core.WebRequest;
import org.vraptor.scope.DefaultLogicRequest;
import org.vraptor.test.MockedHttpSession;
import org.vraptor.test.MockedRequest;
import org.vraptor.test.MockedResponse;
import org.vraptor.test.MockedServletContext;
import org.vraptor.url.DefaultRequestInfo;

public class OverridableViewManagerTest extends AbstractTest {

	private Mock mock;

	private ViewManager manager;

	private MockedRequest request;

	private MockedResponse response;

	private LogicRequest logic;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.mock = mock(ViewManager.class);
		this.manager = (ViewManager) mock.proxy();
		this.request = new MockedRequest("", new MockedHttpSession(new MockedServletContext()));
		this.response = new MockedResponse();
		this.logic = new DefaultLogicRequest(new DefaultRequestInfo("component", "logic"), new WebRequest(request, response,
				createServletContext()));
	}

	public void testDoesntAffectDirectForwarding() throws ViewException {
		mock.expects(once()).method("directForward").withAnyArguments();
		OverridableViewManager overriden = new OverridableViewManager(manager);
		overriden.directForward(null, null, null);
		mock.verify();
	}

	public void testDoesntAffectRedirecting() throws ViewException {
		mock.expects(once()).method("redirect").withAnyArguments();
		OverridableViewManager overriden = new OverridableViewManager(manager);
		overriden.redirect(null, "");
		mock.verify();
	}

	public void testDoesntAffectNonOverridenView() throws ViewException {
		mock.expects(once()).method("forward").with(eq(logic), eq("ok"));
		OverridableViewManager overriden = new OverridableViewManager(manager);
		overriden.forward(logic, "ok");
		mock.verify();
	}

	public void testForwardsOverridenViewThroughDierctForward() throws ViewException {
		mock.expects(once()).method("directForward").with(new Constraint[] { eq(logic), eq("ok"), eq("to") });
		OverridableViewManager overriden = new OverridableViewManager(manager);
		overriden.overrideView("component.logic.ok", "to");
		overriden.forward(logic, "ok");
		mock.verify();
	}

	public void testForwardsOverridenRedirect() throws ViewException {
		mock.expects(once()).method("redirect").with(new Constraint[] { eq(logic), eq("to") });
		OverridableViewManager overriden = new OverridableViewManager(manager);
		overriden.overrideView("component.logic.ok", "redirect:to");
		overriden.forward(logic, "ok");
		mock.verify();
	}

	public void testRedirectToInvalidExpressionGeneratesAnError() {
		OverridableViewManager overriden = new OverridableViewManager(manager);
		try {
			overriden.redirect(createLogicRequest(), "${a.b.c}");
			fail();
		} catch (ViewException e) {
			// ok
		}
	}

}
