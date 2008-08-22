package org.vraptor.plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.jmock.Mock;
import org.vraptor.AbstractTest;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicNotFoundException;

public class ComponentWrapperVisitorTest extends AbstractTest {

	private Mock managerProxy;

	private ComponentManager manager;

	private List<ComponentType> components;

	public void testRegisterRequiredComponents() throws InvalidComponentException, LogicNotFoundException {
		managerProxy.expects(once()).method("register").with(eq(components.get(0)));
		ComponentWrapper wrapper = new ComponentWrapper() {
			public boolean needsToWrap(ComponentType component) throws LogicNotFoundException {
				return true;
			}

			public ComponentType wrap(ComponentType component) {
				return component;
			}
		};
		ComponentWrapperVisitor visitor = new ComponentWrapperVisitor(wrapper);
		visitor.visit(manager);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.managerProxy = mock(ComponentManager.class);
		this.components = Arrays.asList(new ComponentType[] { createComponentType(SimpleComponent.class) });
		managerProxy.expects(once()).method("getComponents").will(returnValue(new HashSet(components)));
		this.manager = (ComponentManager) managerProxy.proxy();
	}

	public static class SimpleComponent {

	}

	public void testIgnoresNotRequiredComponents() throws LogicNotFoundException {
		ComponentWrapper wrapper = new ComponentWrapper() {
			public boolean needsToWrap(ComponentType component) throws LogicNotFoundException {
				return false;
			}

			public ComponentType wrap(ComponentType component) {
				return component;
			}
		};
		ComponentWrapperVisitor visitor = new ComponentWrapperVisitor(wrapper);
		visitor.visit(manager);
	}

}
