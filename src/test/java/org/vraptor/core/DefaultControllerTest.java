package org.vraptor.core;

import junit.framework.TestCase;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.expectation.Expectation;
import org.vraptor.webapp.WebApplication;
import org.vraptor.component.ComponentType;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.LogicMethod;
import org.vraptor.annotations.Component;
import org.vraptor.url.LogicLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * @Author Fabio Kung
 */
public class DefaultControllerTest extends TestCase {

    private final Mockery mockery = new Mockery();

    public void testExecuteIfGetComponentTypeFromComponentManagerInsteadOfLogicMethod() throws Exception {
        final WebApplication webApplication = mockery.mock(WebApplication.class);
        final LogicMethod logicMethod = mockery.mock(LogicMethod.class);
        final ComponentType expectedType = mockery.mock(ComponentType.class, "expected");

        mockery.checking(new Expectations() {
            {
                ComponentManager manager = mockery.mock(ComponentManager.class);
                ComponentType componentType = mockery.mock(ComponentType.class);

                one(componentType).getName();
                will(returnValue("component"));

                atLeast(1).of(logicMethod).getName();
                will(returnValue("logic"));

                atLeast(1).of(logicMethod).getComponentType();
                will(returnValue(componentType));

                atLeast(1).of(webApplication).getComponentManager();
                will(returnValue(manager));

                atLeast(1).of(manager).getComponent("component", "logic");
                will(returnValue(expectedType));
            }});

        ComponentType registeredType = new DefaultController(webApplication).getComponentType(logicMethod);
        assertEquals(expectedType, registeredType);
        mockery.assertIsSatisfied();
    }

}
