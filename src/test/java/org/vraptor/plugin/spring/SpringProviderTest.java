package org.vraptor.plugin.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.Resource;
import org.vraptor.AbstractTest;
import org.vraptor.introspector.MapBasedProvider;
import org.vraptor.introspector.WebBeanProvider;
import org.vraptor.scope.DefaultLogicRequest;

public class SpringProviderTest extends AbstractTest {

	private MockedSpringAppContext app;

	private MapBasedProvider beanProvider;

	private SpringProvider provider;

	class MockedSpringAppContext implements ApplicationContext {

		private final Map<String, Object> beans = new HashMap<String, Object>();

		public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
			return null;
		}

		public String getDisplayName() {
			return null;
		}

		public ApplicationContext getParent() {
			return null;
		}

		public long getStartupDate() {
			return 0;
		}

		public boolean containsBeanDefinition(String arg0) {
			return false;
		}

		public int getBeanDefinitionCount() {
			return 0;
		}

		public String[] getBeanDefinitionNames() {
			return null;
		}

		public String[] getBeanNamesForType(Class arg0) {
			return null;
		}

		public String[] getBeanNamesForType(Class arg0, boolean arg1, boolean arg2) {
			return null;
		}

		public Map getBeansOfType(Class arg0) throws BeansException {
			return null;
		}

		public Map getBeansOfType(Class arg0, boolean arg1, boolean arg2) throws BeansException {
			return null;
		}

		public boolean containsBean(String arg0) {
			return beans.containsKey(arg0);
		}

		public String[] getAliases(String arg0) {
			return null;
		}

		public Object getBean(String arg0) throws BeansException {
			return beans.get(arg0);
		}

		public Object getBean(String arg0, Class arg1) throws BeansException {
			return null;
		}

		public Class getType(String arg0) throws NoSuchBeanDefinitionException {
			return null;
		}

		public boolean isSingleton(String arg0) throws NoSuchBeanDefinitionException {
			return false;
		}

		public boolean containsLocalBean(String arg0) {
			return false;
		}

		public BeanFactory getParentBeanFactory() {
			return null;
		}

		public String getMessage(MessageSourceResolvable arg0, Locale arg1) throws NoSuchMessageException {
			return null;
		}

		public String getMessage(String arg0, Object[] arg1, Locale arg2) throws NoSuchMessageException {
			return null;
		}

		public String getMessage(String arg0, Object[] arg1, String arg2, Locale arg3) {
			return null;
		}

		public void publishEvent(ApplicationEvent arg0) {

		}

		public Resource[] getResources(String arg0) throws IOException {
			return null;
		}

		public ClassLoader getClassLoader() {
			return null;
		}

		public Resource getResource(String arg0) {
			return null;
		}

		public void put(String key, Object obj) {
			this.beans.put(key, obj);
		}

	}

	protected void setUp() throws Exception {
		super.setUp();
		app = new MockedSpringAppContext();
		beanProvider = new MapBasedProvider();
		this.provider = new SpringProvider(this.beanProvider, this.app);
	}

	public void testFindsSpringAttribute() {
		Object value = new Object();
		app.put("key", value);
		Object found = provider.findAttribute(null, "key");
		assertEquals(value, found);
	}

	public void testSearchsForAttributeFirstInSpring() {
		Object value = new Object();
		app.put("key", value);
		beanProvider.put("key", new Object());
		Object found = provider.findAttribute(null, "key");
		assertEquals(value, found);
	}

	public void testUsesTheWrappedProviderIfAttributeIsNotFound() {
		Object value = new Object();
		WebBeanProvider beanProvider = new WebBeanProvider();
		DefaultLogicRequest req = createLogicRequest();
		req.getRequestContext().setAttribute("key", value);
		this.provider = new SpringProvider(beanProvider, this.app);
		Object found = provider.findAttribute(req, "key");
		assertEquals(value, found);
	}

}
