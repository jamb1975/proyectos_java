/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.LastNumberNoFactura;
import service.exceptions.NonexistentEntityException;

/**
 *
 * @author karolyani
 */
public class LastNumberNoFacturaJpaController implements Serializable {

    public LastNumberNoFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LastNumberNoFactura lastNumberNoFactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lastNumberNoFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LastNumberNoFactura lastNumberNoFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lastNumberNoFactura = em.merge(lastNumberNoFactura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = lastNumberNoFactura.getId();
                if (findLastNumberNoFactura(id) == null) {
                    throw new NonexistentEntityException("The lastNumberNoFactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LastNumberNoFactura lastNumberNoFactura;
            try {
                lastNumberNoFactura = em.getReference(LastNumberNoFactura.class, id);
                lastNumberNoFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lastNumberNoFactura with id " + id + " no longer exists.", enfe);
            }
            em.remove(lastNumberNoFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LastNumberNoFactura> findLastNumberNoFacturaEntities() {
        return findLastNumberNoFacturaEntities(true, -1, -1);
    }

    public List<LastNumberNoFactura> findLastNumberNoFacturaEntities(int maxResults, int firstResult) {
        return findLastNumberNoFacturaEntities(false, maxResults, firstResult);
    }

    private List<LastNumberNoFactura> findLastNumberNoFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LastNumberNoFactura.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public LastNumberNoFactura findLastNumberNoFactura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LastNumberNoFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getLastNumberNoFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LastNumberNoFactura> rt = cq.from(LastNumberNoFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
