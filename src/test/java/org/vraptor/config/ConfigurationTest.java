package org.vraptor.config;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.net.URL;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.vraptor.annotations.Component;
import org.vraptor.component.ComponentManager;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.webapp.WebApplication;

public class ConfigurationTest extends TestCase {

	private Mockery mockery;
	private ApplicationContext context;
	private Configuration configuration;
	private WebApplication app;
	private ComponentManager manager;

	{
		mockery = new Mockery();
		context = mockery.mock(ApplicationContext.class);
		manager = mockery.mock(ComponentManager.class);
		configuration = new Configuration(context,manager);
		app = mockery.mock(WebApplication.class);
	}
	
	public void testParsesSimpleClassNameWithPackage() {
		assertEquals("br.com.caelum.Teste", new Configuration(null, null).parseClassName("/WEB-INF/classes/",
				"/WEB-INF/classes/br/com/caelum/Teste.class"));
	}

	public void testParsesSimpleClassNameWithMixedPatterns() {
		assertEquals("br.com.caelum.Teste", new Configuration(null, null).parseClassName("/WEB-INF/classes/",
				"/WEB-INF/classes/br/com/caelum\\Teste.class"));
	}

	public void testParsesSimpleClassNameWithWindowsPattern() {
		assertEquals("br.com.caelum.Teste", new Configuration(null, null).parseClassName("/WEB-INF/classes/",
				"/WEB-INF/classes/br\\com\\caelum\\Teste.class"));
	}

	public void testParsesSimpleClassNameWithoutPackage() {
		assertEquals("Teste", new Configuration(null, null).parseClassName("/WEB-INF/classes/",
				"/WEB-INF/classes/Teste.class"));
	}

	public void testLoadWebApplicationWithDirectoryNotFound() throws ConfigException {
		
		
		mockery.checking(new Expectations() {
			{
				one(context).getResourcePaths((String) with(a(String.class)));
				will(returnValue(null));
			}
		});
		configuration.load(app);
		mockery.assertIsSatisfied();
	}
	public void testLoadWebApplicationWithAComponent() throws Exception {
        File root = new File(AComponent.class.getResource("/").toURI());
        final String classLocation = AComponent.class.getName().replace(".", "/") + ".class";
        final String pathname = root.getAbsolutePath() + "/" +  classLocation;
        final File component = new File(pathname);
        assertTrue(component.exists());
        mockery.checking(new Expectations() {
            {
                one(context).getResourcePaths((String) with(a(String.class)));
                String path = "/WEB-INF/classes/" + classLocation;
                will(returnValue(new HashSet<String>(Arrays.asList(path))));
                one(context).getRealPath(path);
                will(returnValue(pathname));
                try {
                    one(manager).register(AComponent.class.getName());
                } catch (Exception e) {
                    fail(e.getMessage());
                }
            }
        });

        configuration.load(app);
        mockery.assertIsSatisfied();
    }
	
	@Component
	public static class AComponent {
		
	}
}
