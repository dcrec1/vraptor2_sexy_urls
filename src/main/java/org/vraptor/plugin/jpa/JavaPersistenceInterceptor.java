package org.vraptor.plugin.jpa;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentType;
import org.vraptor.component.LogicMethod;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

/**
 * Manages the {@link EntityManager} and transactions lifecycle, in a
 * request-based manner
 *
 * @author Fabio Kung
 * @since 2.3.2
 */
public class JavaPersistenceInterceptor implements Interceptor {
	private static final Logger LOG = Logger.getLogger(JavaPersistenceInterceptor.class);

	private static final Map<String, EntityManagerFactory> FACTORIES = new HashMap<String, EntityManagerFactory>();

	private EntityManager entityManager;

	private final EntityManagerIntrospector introspector = new EntityManagerIntrospector();

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		ComponentType componentType = flow.getLogicRequest().getLogicDefinition().getComponentType();
		LogicMethod logicMethod = flow.getLogicRequest().getLogicDefinition().getLogicMethod();
		LOG.debug(MessageFormat.format("Preparing PersistenceContext for: {0}", componentType.getName()));

		String persistenceUnitName = introspector.getPersistenceUnitName(componentType);
		LOG.debug(MessageFormat.format("Retrieving the EntityManagerFactory for the PersistenceUnit: {0}.",
				persistenceUnitName));
		EntityManagerFactory factory;
		synchronized (FACTORIES) {
			factory = FACTORIES.get(persistenceUnitName);
			if (factory == null) {
				LOG.debug(MessageFormat.format(
						"EntityManagerFactory not yet built. Creating one for PersistenceUnit {0}.",
						persistenceUnitName));
				factory = Persistence.createEntityManagerFactory(persistenceUnitName);
				FACTORIES.put(persistenceUnitName, factory);
			}
		}

		LOG.info("Creating an EntityManager for the request...");
		EntityManager original = factory.createEntityManager();
		this.entityManager = wrap(original);

		try {
			boolean transactionRequired = this.introspector.isTransactionRequired(logicMethod);
			if (transactionRequired) {
				original.getTransaction().begin();
			}
			flow.execute();
			if (transactionRequired && original.getTransaction().isActive()
					&& !original.getTransaction().getRollbackOnly()) {
				original.getTransaction().commit();
			}
		} catch (Throwable t) {
			EntityTransaction transaction = original.getTransaction();
			if (transaction != null && transaction.isActive()) {
				LOG.error("Problem ocurred. The transaction will be rolled back.");
				transaction.rollback();
			}
			throw new LogicException("An error was ocurred. If there were a transaction, it was rolled back.", t);
		} finally {
			LOG.info("Closing the request EntityManager");
			original.close();
		}
	}

	private EntityManager wrap(EntityManager entityManager) {
		LOG.trace("Wrapping the original EntityManager in the VRaptorEntityManager (with close and joinTransaction disabled).");
		return new VRaptorEntityManager(entityManager);
	}

	@Out(scope = ScopeType.REQUEST, key = "javax.persistence.EntityManager")
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
