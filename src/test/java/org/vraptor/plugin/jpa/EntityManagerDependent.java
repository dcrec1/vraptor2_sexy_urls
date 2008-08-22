package org.vraptor.plugin.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vraptor.annotations.Component;

/**
 * @author Fabio Correia Kung
 *
 */
@Component
public class EntityManagerDependent {
	@PersistenceContext
	private EntityManager entityManager;

	public void logic() {
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}

