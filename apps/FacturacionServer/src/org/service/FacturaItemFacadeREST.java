/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Factura;
import model.FacturaItem;
import model.Producto;
import model.TempFacturaItems;
import model.TempMesasFactura;


/**
 *
 * @author karol
 */
@Stateless
@Path("model.facturaitem")
public class FacturaItemFacadeREST  {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
    private static final String FIND_ITEMSFACT_QUERY = 
            "select "
                + "tf"
                + " from Factura f, "
                + "TempMesasFactura tf "
                +" where  f.id=tf.factura.id order by tf.m_iNoMesa";
    public FacturaItemFacadeREST() {
        
    }

    

   

    

    @GET
    @Produces({"application/xml", "application/json"})
    public List<TempFacturaItems> findAll() 
    {
         List<TempMesasFactura> _listTempFactura=em.createQuery(FIND_ITEMSFACT_QUERY).getResultList();
         List<TempFacturaItems>_listTempFacturaItems=new ArrayList<TempFacturaItems>();
         TempFacturaItems tf=new TempFacturaItems();
        for(TempMesasFactura tempf:_listTempFactura)
        {
            
          tf=null;
          tf=new TempFacturaItems();  
          tf.setM_dFacturaDate(tempf.getFactura().getFacturaDate());
          tf.setM_iNoMesa(tempf.getFactura().getMt_NoMesa());
          tf.setM_lIdFact(tempf.getFactura().getId());
          tf.setM_LIdTempMesa(tempf.getId());
          tf.setCredito(tempf.getFactura().isCredito());
          if(tempf.getFactura().getCustomer()!=null)
          {
           tf.setM_lIdTerc(tempf.getFactura().getCustomer().getId());
           tf.setM_sCelular(tempf.getFactura().getCustomer().getCelular());
           tf.setM_sDir1(tempf.getFactura().getCustomer().getDir1());
           tf.setM_sEmail(tempf.getFactura().getCustomer().getEmail());
           tf.setM_sNoIdent(tempf.getFactura().getCustomer().getNo_ident());
           tf.setM_sNombre(tempf.getFactura().getCustomer().getNombre());
          }
          List<FacturaItem> _LFacturaItems=new ArrayList<FacturaItem>();
          for(FacturaItem fi:tempf.getFactura().getFacturaItems())
          {
              
              FacturaItem _facFacturaItem=new FacturaItem();
              _facFacturaItem.setId(fi.getId());
              _facFacturaItem.setProduct(fi.getProduct());
              _facFacturaItem.addQuantity(fi.getQuantity());
              _LFacturaItems.add(_facFacturaItem);
             
              _facFacturaItem=null;
          }
          tf.setFacturaItem(_LFacturaItems);
          _listTempFacturaItems.add(tf);
         // System.out.println("Id fact:"+_listTempFacturaItems.get(0).getM_lIdFact()+" Producto:"+_listTempFacturaItems.get(0).getFacturaItem().get(0).getProduct().getNombre()+" Cant Producto:"+_listTempFacturaItems.get(0).getFacturaItem().get(0).getQuantity()+" No Mesa:"+_listTempFacturaItems.get(0).getM_iNoMesa());
    
        }
     
         return _listTempFacturaItems;
    }

    

  @GET
  @Path("{idFact}/{idProd}")
  public void remove(@PathParam("idFact") Long _lIdFact, @PathParam("idProd") Long _lIdProd) {
      Factura factura=em.find(Factura.class,_lIdFact);
      Producto producto=new Producto();
      producto.setId(_lIdProd);
      factura.removeProduct(producto);
      try{
      factura=em.merge(factura);
      
      FacturaItem _facturaItem=(FacturaItem)em.createQuery("select fi from FacturaItem fi where fi.product=:p and fi.factura=:f")
                                  .setParameter("p", producto)
                                  .setParameter("f", factura)
                                  .getSingleResult();
      em.remove(_facturaItem);
      System.out.println("eliminado "+ producto.getId());
      }catch(Exception e){
          System.out.println(" no eliminado "+ producto.getId());
      }
      
    }
    
}
