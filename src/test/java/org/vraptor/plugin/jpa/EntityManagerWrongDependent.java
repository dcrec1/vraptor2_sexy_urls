package org.vraptor.plugin.jpa;

import javax.persistence.EntityManager;

import org.vraptor.annotations.Component;

/**
 * @author Fabio Correia Kung
 *
 */
@Component
public class EntityManagerWrongDependent {
	// @PersistenceContext missing the annotation
	private EntityManager entityManager;

	public void logic() {
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
