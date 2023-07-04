/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.Pedidos;


/**
 *
 * @author isoft
 */
public class PedidosController {
    
 public void create(Pedidos pedido)
 {
    if(EntityManagerGeneric.et.isActive())
    { 
        EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(pedido);
    EntityManagerGeneric.et.commit();
 }
 public void update(Pedidos pedido)
 {
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.merge(pedido);
    EntityManagerGeneric.et.commit();
 }
 public List<Pedidos>  getrecordspedidosporestado(int estado)
 {
    return  EntityManagerGeneric.em.createQuery("select p from Pedidos p where p.estado=:pestado")
                                   .setParameter("pestado", estado)
                                   .getResultList();
 }
 public Pedidos getfindbynopedido(String nopedido)
 {
     try
     {
      return  (Pedidos)EntityManagerGeneric.em.createQuery("select p from Pedidos p where p.no_pedido=:pnopedido")
                                   .setParameter("pnopedido", nopedido)
                                   .getSingleResult();
     }catch(Exception e)
     {
         return null;
     }
 }
}
