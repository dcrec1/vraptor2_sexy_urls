package org.vraptor.core;

import org.vraptor.LogicException;
import org.vraptor.component.ComponentNotFoundException;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.component.LogicNotFoundException;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.StaticResourceException;
import org.vraptor.interceptor.InterceptorInstantiationException;
import org.vraptor.introspector.Introspector;
import org.vraptor.url.InvalidURLException;
import org.vraptor.view.ViewException;
import org.vraptor.webapp.WebApplication;

/**
 * Main vraptor2 controller.
 *
 * @author Guilherme Silveira
 */
public class DefaultController implements Controller {

    private final WebApplication application;

    /**
     * @param application
     */
    public DefaultController(WebApplication application) {
        this.application = application;
    }

    /* (non-Javadoc)
      * @see org.vraptor.core.Controller#getWebApplication()
      */
    public WebApplication getWebApplication() {
        return application;
    }

    /* (non-Javadoc)
      * @see org.vraptor.core.Controller#execute(org.vraptor.core.WebRequest)
      */
    public String execute(WebRequest request) throws InvalidURLException, ComponentNotFoundException,
            LogicNotFoundException, ViewException, InterceptorInstantiationException, LogicException, StaticResourceException {
        LogicMethod method = getWebApplication().getLogicLocator().locate(request.getRequest());
        ComponentType componentType = getComponentType(method);

        return new VRaptorExecution(componentType, method, this, request).execute();
    }

    ComponentType getComponentType(LogicMethod method) throws ComponentNotFoundException, LogicNotFoundException {
        String componentName = method.getComponentType().getName();
        ComponentManager manager = getWebApplication().getComponentManager();
        ComponentType componentType = manager.getComponent(componentName, method.getName());
        return componentType;
    }

    /* (non-Javadoc)
      * @see org.vraptor.core.Controller#getIntrospector()
      */
    public Introspector getIntrospector() {
        return application.getIntrospector();
    }

}
