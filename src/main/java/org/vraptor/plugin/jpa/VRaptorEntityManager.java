package org.vraptor.plugin.jpa;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: fck
 * Date: Feb 28, 2007
 * Time: 2:33:40 PM
 */
class VRaptorEntityManager implements EntityManager {

    private EntityManager original;

    public VRaptorEntityManager(EntityManager original) {
        this.original = original;
    }

    public void joinTransaction() {
        throw new UnsupportedOperationException("Only valid in a JTA environment.");
    }

    public void close() {
        throw new UnsupportedOperationException("VRaptor will handle the lifecycle for you.");
    }

    public Object getDelegate() {
        return original.getDelegate();
    }

    public void persist(Object o) {
        original.persist(o);
    }

    public <T> T merge(T t) {
        return original.merge(t);
    }

    public void remove(Object o) {
        original.remove(o);
    }

    public <T> T find(Class<T> aClass, Object o) {
        return original.find(aClass, o);
    }

    public <T> T getReference(Class<T> aClass, Object o) {
        return original.getReference(aClass, o);
    }

    public void flush() {
        original.flush();
    }

    public void setFlushMode(FlushModeType flushModeType) {
        original.setFlushMode(flushModeType);
    }

    public FlushModeType getFlushMode() {
        return original.getFlushMode();
    }

    public void lock(Object o, LockModeType lockModeType) {
        original.lock(o, lockModeType);
    }

    public void refresh(Object o) {
        original.refresh(o);
    }

    public void clear() {
        original.clear();
    }

    public boolean contains(Object o) {
        return original.contains(o);
    }

    public Query createQuery(String s) {
        return original.createQuery(s);
    }

    public Query createNamedQuery(String s) {
        return original.createNamedQuery(s);
    }

    public Query createNativeQuery(String s) {
        return original.createNativeQuery(s);
    }

    public Query createNativeQuery(String s, Class aClass) {
        return original.createNativeQuery(s, aClass);
    }

    public Query createNativeQuery(String s, String s1) {
        return original.createNativeQuery(s, s1);
    }

    public boolean isOpen() {
        return original.isOpen();
    }

    public EntityTransaction getTransaction() {
        return original.getTransaction();
    }
}
