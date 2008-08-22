package org.vraptor.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.vraptor.AbstractTest;
import org.vraptor.annotations.In;
import org.vraptor.component.BeanConstructor;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.FieldAnnotation;
import org.vraptor.component.InvalidComponentException;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.interceptor.InterceptorType;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.introspector.ReadParameter;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.reflection.GettingException;
import org.vraptor.reflection.MethodInvocationException;
import org.vraptor.scope.ScopeType;
import org.vraptor.util.StatsQuery.ComponentTypeNameComparator;
import org.vraptor.webapp.DefaultWebApplication;
import org.vraptor.webapp.WebApplication;

public class StatsQueryTest extends AbstractTest {

	public void testLogicName() throws InvalidComponentException, ComponentInstantiationException, LogicNotFoundException {

		boolean found = false;
		ComponentType statsQuery = registry.getComponentManager().getComponentType(StatsQuery.class);

		for (LogicMethod logic : statsQuery.getLogics()) {
			if ("read".equals(logic.getName())) {
				found = true;
			}
		}

		assertTrue("Logic name read not found", found);
	}

	public void testChecksDifferentComponentsGetSortedByName() {
		StatsQuery query = new StatsQuery(createApp());
		ComponentTypeNameComparator comp = query.new ComponentTypeNameComparator();
		assertTrue(comp.compare(new MockType("a"), new MockType("b")) < 0);
	}

	private WebApplication createApp() {
		return new DefaultWebApplication(createServletContext());
	}

	public void testSortsComponentsWithSameName() {
		StatsQuery query = new StatsQuery(createApp());
		ComponentTypeNameComparator comp = query.new ComponentTypeNameComparator();
		assertTrue(comp.compare(new MockType("a"), new MockType("a")) == 0);
	}

	class MockType implements ComponentType {

		private String name;

		public MockType(String n) {
			this.name = n;
		}

		public Class getComponentClass() {
			return null;
		}

		public String getDestroyLogicName() {
			return null;
		}

		public List<FieldAnnotation<In>> getInAnnotations() {
			return null;
		}

		public List<InterceptorType> getInterceptors() {
			return null;
		}

		public String getKey() {
			return null;
		}

		public LogicMethod getLogic(String key) throws LogicNotFoundException {
			return null;
		}

		public Collection<LogicMethod> getLogics() {
			return null;
		}

		public String getName() {
			return name;
		}

		public List<ReadParameter> getReadParameters() {
			return null;
		}

		public ScopeType getScope() {
			return null;
		}

		public Object newInstance(BeanProvider provider) throws ComponentInstantiationException {
			return null;
		}

		public Map<String, Object> getOutjectedValues(Object comp, ScopeType scope) throws GettingException, MethodInvocationException {
			return null;
		}

		public BeanConstructor getConstructor() {
			return null;
		}

	}

	public void testOutjectsAllComponents() throws InvalidComponentException, LogicNotFoundException {
		WebApplication app = createApp();
		ComponentType c = createComponentType(Component.class);
		app.getComponentManager().register(c);
		StatsQuery query = new StatsQuery(app);
		query.read();
		assertTrue(query.getComponents().contains(c));
	}

	public void testOutjectsAllPlugins() throws InvalidComponentException {
		WebApplication app = createApp();
		SimplePlugin plugin = new SimplePlugin();
		app.getPluginManager().register(plugin);
		StatsQuery query = new StatsQuery(app);
		query.read();
		assertTrue(query.getPlugins().contains(plugin));
	}
	
	public static class SimplePlugin implements VRaptorPlugin {
		public void init(WebApplication application) {
		}		
	}

	public static class Component {
		public void logic() {
		}
	}

}
