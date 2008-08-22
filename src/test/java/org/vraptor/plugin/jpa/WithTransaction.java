package org.vraptor.plugin.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vraptor.annotations.Component;

/**
 * @author Fabio Correia Kung
 *
 */
@Component
public class WithTransaction {
	@PersistenceContext
	private EntityManager entityManager;

	@Transaction(required=true)
	public void logic() {
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}

