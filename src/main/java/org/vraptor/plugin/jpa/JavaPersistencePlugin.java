package org.vraptor.plugin.jpa;

import org.apache.log4j.Logger;
import org.vraptor.VRaptorException;
import org.vraptor.component.ComponentManager;
import org.vraptor.component.ComponentType;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: fck
 * Date: Feb 28, 2007
 * Time: 11:30:45 AM
 */
public class JavaPersistencePlugin implements VRaptorPlugin {
    private static final Logger LOG = Logger.getLogger(JavaPersistencePlugin.class);

    private final EntityManagerIntrospector introspector = new EntityManagerIntrospector();

    public void init(WebApplication application) throws VRaptorException {
        ComponentManager manager = application.getComponentManager();
        for (ComponentType component : manager.getComponents()) {
            if (shouldBeWrapped(component)) {
                LOG.debug(MessageFormat.format("Adding persistence functionality to the component {0}",
                        component));
                manager.register(wrapComponent(component));
            }
        }

    }

    private ComponentType wrapComponent(ComponentType component) {
        return new JavaPersistenceComponent(component, introspector);
    }

    private boolean shouldBeWrapped(ComponentType component) {
        return introspector.dependsOnEntityManager(component) ||
                introspector.hasPersistenceContext(component);
    }

}
