package org.vraptor.plugin.jpa;

import javax.persistence.EntityManager;

import org.vraptor.annotations.Component;

/**
 * @author Fabio Correia Kung
 *
 */
@Component
public class DependentByConstructor {
	private EntityManager entityManager;

	public DependentByConstructor(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void logic() {
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
