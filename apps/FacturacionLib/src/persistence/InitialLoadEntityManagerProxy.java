/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author karol
 */
public class InitialLoadEntityManagerProxy implements EntityManager{
//@PersistenceContext(unitName = "DataAppLibraryPU")
    private EntityManager em;
   
private EntityTransaction et;
public InitialLoadEntityManagerProxy ( ){
        em= Persistence.createEntityManagerFactory("FacturacionLibPU").createEntityManager();
        init();
    }
public InitialLoadEntityManagerProxy (EntityManager em ){
        this.em = em;
    }
    @Override
    public void persist(Object o) {
        em.persist(o);
        end();
   }

    @Override
    public <T> T merge(T t) {
    return em.merge(t);
    }

    @Override
    public void remove(Object o) {
        em.remove(o);
    }

    @Override
    public <T> T find(Class<T> type, Object o) {
        return em.find(type, o);
    }

    @Override
    public <T> T find(Class<T> type, Object o, Map<String, Object> map) {
     return em.find(type, o,map);
    }

    @Override
    public <T> T find(Class<T> type, Object o, LockModeType lmt) {
    return em.find(type, o,lmt);
    }

    @Override
    public <T> T find(Class<T> type, Object o, LockModeType lmt, Map<String, Object> map) {
        return em.find(type,o,lmt,map);
    }

    @Override
    public <T> T getReference(Class<T> type, Object o) {
        return em.getReference(type, o);
    }

    @Override
    public void flush() {
      em.flush();
    }

    @Override
    public void setFlushMode(FlushModeType fmt) {
    em.setFlushMode(fmt);
    }

    @Override
    public FlushModeType getFlushMode() {
        return em.getFlushMode();
    }

    @Override
    public void lock(Object o, LockModeType lmt) {
      em.lock(o, lmt);
    }

   
    @Override
    public void lock(Object o, LockModeType lmt, Map<String, Object> map) {
        em.lock(o, lmt, map);
    }

    @Override
    public void refresh(Object o) {
        em.refresh(o);
    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {
        em.refresh(o, map);
    }

    @Override
    public void refresh(Object o, LockModeType lmt) {
        em.refresh(o, lmt);
    }

    @Override
    public void refresh(Object o, LockModeType lmt, Map<String, Object> map) {
        em.refresh(o, lmt, map);
    }

    @Override
    public void clear() {
        em.clear();
    }

    @Override
    public void detach(Object o) {
        em.detach(o);
    }

    @Override
    public boolean contains(Object o) {
        return em.contains(o);
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return em.getLockMode(o);
    }

    @Override
    public void setProperty(String string, Object o) {
        em.setProperty(string, o);
    }

    @Override
    public Map<String, Object> getProperties() {
        return em.getProperties();
    }

    @Override
    public Query createQuery(String string) {
        return em.createQuery(string);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> cq) {
        return em.createQuery(cq);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String string, Class<T> type) {
        return em.createQuery(string, type);
    }

    @Override
    public Query createNamedQuery(String string) {
        return em.createNamedQuery(string);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String string, Class<T> type) {
        return em.createNamedQuery(string, type);
    }

    @Override
    public Query createNativeQuery(String string) {
        return em.createNamedQuery(string);
    }

    @Override
    public Query createNativeQuery(String string, Class type) {
        return em.createNativeQuery(string, type);
    }

    @Override
    public Query createNativeQuery(String string, String string1) {
        return em.createNativeQuery(string, string1);
    }

    @Override
    public void joinTransaction() {
        em.joinTransaction();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return em.unwrap(type);
    }

    @Override
    public Object getDelegate() {
        return em.getDelegate();
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public boolean isOpen() {
        return em.isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return em.getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return em.getMetamodel();
    }

    @Override
    public Query createQuery(CriteriaUpdate cu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaDelete cd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string, Class... types) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string, String... strings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isJoinedToTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntityGraph<?> createEntityGraph(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntityGraph<?> getEntityGraph(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void init()
    {
         et = em.getTransaction();
        et.begin();
    }
    public void end()
    {
         et.commit();
        // em.close();
    }
    
}
