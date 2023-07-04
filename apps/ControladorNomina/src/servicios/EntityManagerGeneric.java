/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author karolyani
 */
public class EntityManagerGeneric {
        @PersistenceContext(unitName = "ControladorNominaPU")
        public static final EntityManager em=Persistence.createEntityManagerFactory("ControladorNominaPU").createEntityManager();
        public static final EntityTransaction et=em.getTransaction();
        @PersistenceContext(unitName = "ControladorNominaPU")
        public static final EntityManager em2=Persistence.createEntityManagerFactory("ControladorNominaPU").createEntityManager();
        public static final EntityTransaction et2=em2.getTransaction();
        private static EntityManagerFactory emf =Persistence.createEntityManagerFactory("ControladorNominaPU");
        private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();
        private static ConcurrentHashMap<String, EntityManager> entityManagers = new ConcurrentHashMap<String, EntityManager>();

        public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
        em = emf.createEntityManager();
        entityManagers.put(em.toString(), em);
        threadLocal.set(em);
    }
    return em;
        }
        
     public static <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    getEntityManager().clear();
    return getEntityManager().createQuery(qlString, resultClass);
}

public static Query createQuery(String qlString) {
    return getEntityManager().createQuery(qlString);
}

public static void closeEntityManager() {
    EntityManager em = threadLocal.get();
    if (em != null) {
        entityManagers.remove(em.toString());
        
        em.close();
        threadLocal.set(null);
    }
}

public static void clearEntityManager() {
    EntityManager em = threadLocal.get();
    if (em != null) {
        em.clear();
    }
}

public static void closeEntityManagerFactory() {
    emf.close();
    emf = null;
}

public static void beginTransaction() {
    getEntityManager().getTransaction().begin();
}

public static void rollback() {
    getEntityManager().getTransaction().rollback();
}

public static void commit() {
    getEntityManager().getTransaction().commit();
}

public static void detach(Object entity) {
    Iterator it = entityManagers.values().iterator();

    while (it.hasNext()) {
        EntityManager em = (EntityManager) it.next();
        if (em != null || em.isOpen())
            em.detach(entity);
    }
}
public static void cerrartodaslasconexiones() {
    Iterator it = entityManagers.values().iterator();
int i=0;
    while (it.hasNext()) {
        EntityManager em = (EntityManager) it.next();
        if (em != null || em.isOpen())
        {  entityManagers.remove(em.toString());
       // em.flush();  
        em.close();
          
        threadLocal.set(null);
        i++;
        
        }
    }
}

/*public static void evict(Object entity) {
    Iterator it = entityManagers.values().iterator();

    while (it.hasNext()) {
        EntityManager em = (EntityManager) it.next();
        if (em != null || em.isOpen())
            em.getEntityManagerFactory().getCache().evict(entity.getClass(), ((EntityBase) entity).getId());
        //em.getEntityManagerFactory().createEntityManager(SynchronizationType.)

    }
}*/

public static void merge(Object entity) {
    Iterator it = entityManagers.values().iterator();

    while (it.hasNext()) {
        EntityManager em = (EntityManager) it.next();
        if (em != null || em.isOpen())
            em.merge(entity);
    }
}

public static void refresh(Object entity) {
    Iterator it = entityManagers.values().iterator();
    while (it.hasNext()) {
        EntityManager em = (EntityManager) it.next();
        if (em != null || em.isOpen())
            em.refresh(entity);

    }
}

public static boolean isActive() {
    return getEntityManager().getTransaction().isActive();

}

public static boolean isOpen() {
    return emf.isOpen();
}

public static void evictAll() {
    emf.getCache().evictAll();

}



   
}
