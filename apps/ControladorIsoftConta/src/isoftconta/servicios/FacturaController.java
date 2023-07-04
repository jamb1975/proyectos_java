/*                     
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import isoftconta.servicios.EntityManagerGeneric;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import model.ConsecutivoFactura;
import modelo.Factura;
import modelo.Kardex;
import modelo.Producto;
import modelo.Terceros;



/**
 *
 * @author karolyani
 */
public class FacturaController {
    
   private boolean recordexist=true;
     
    public synchronized Factura edit(Factura factura, Producto producto) 
    {    
        Kardex kardex=new Kardex();
         Producto prod=new Producto();
       try{
            
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
     
      if(factura.getId()!=null)
      { 
          
        if(EntityManagerGeneric.et.isActive())
       {
       EntityManagerGeneric.et.rollback();
       }
      EntityManagerGeneric.et.begin();
      factura=EntityManagerGeneric.em.merge(factura);
    
      
      
      Long i;
      
        Kardex kard;
        String message="";
       if(producto.getTipo()==null)
       {
           producto.setTipo("0");
       }
       
       if(!producto.getTipo().equals("1"))
       {
       try{
           System.out.println("id prod->"+producto.getId());
                           kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=(select max(k.id) from Kardex k where k.producto.id=:id)")
	                             .setParameter("id", producto.getId())
	                             .getSingleResult();
                           kardex.setId(kard.getId());
                           kardex.setTipo(factura.getQuantity()>0?"sp":"dv");
                           kardex.setProducto(producto);
                          
                           if(kardex.getTipo().equals("sp"))
                           {
                             kardex.setCantidad_salida(factura.getQuantity());
                             kardex.setCantidad_saldo(kard.getCantidad_saldo()-(kardex.getCantidad_salida())); 
                             kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		     kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_salida()));
                      
                             message="Salida Producto, Factura de Venta N° "+  factura.getNofacturacerosizquierda();
                           }
                           else
                           {
                                   kardex.setCantidad_salida(factura.getQuantity()*(-1));
                                    kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		            kardex.setValor_saldo(kard.getValor_saldo().add(kardex.getValor_salida()));
                      
                                   kardex.setCantidad_saldo(kard.getCantidad_saldo()+(kardex.getCantidad_salida()));
                                    message="Devolución Producto, Factura de Venta N° "+  factura.getNofacturacerosizquierda();
                        
                           }
                           kardex.setDesc_kar(message);
           		 
                           System.out.println("cant saldo actual->"+kard.getCantidad_saldo()+"cant saldo actual2->"+kardex.getCantidad_saldo()+"cant sal->"+kardex.getCantidad_salida()+" valor unidad->"+kard.getValor_unidad()+" Akrd valor saldo->"+kard.getValor_saldo());
                             if(kardex.getCantidad_saldo()>0)
                           {
	    		   kardex.setValor_unidad(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),RoundingMode.UP));
                           }
                           else
                           {
                             kardex.setValor_unidad(kard.getValor_unidad());
                       
                           }
                         
                       
          
       }catch(Exception e)
       {
                          e.printStackTrace();
                           kardex.setTipo("sp");
                           kardex.setProducto(producto);
                           kardex.setCantidad_salida(factura.getQuantity());
                           message="Salida Producto, Factura de Venta N° "+  factura.getNofacturacerosizquierda();
                           kardex.setDesc_kar(message);
           		   kardex.setCantidad_saldo(0-kardex.getCantidad_salida());
                           kardex.setValor_salida(BigDecimal.valueOf(0));
	    		   kardex.setValor_saldo(BigDecimal.valueOf(0));
                           kardex.setValor_unidad(BigDecimal.ZERO);
                           
                           
       }
       } 
       
                EntityManagerGeneric.em.merge(kardex);           
                EntityManagerGeneric.et.commit();                             
      }  
        
        }catch(Exception e)
        {
            e.printStackTrace();
           return null;
        }
      
      
      return factura;
    }
    public Factura nuevafactura(Factura factura,ConsecutivoFactura consecutivoFactura)
    {
        
            try
         {
         if(EntityManagerGeneric.et.isActive())
             {
                 EntityManagerGeneric.et.rollback();
             }
          factura.setNo_factura(ConsecutivoFacturaController.getNoConsecutivo());
                   factura.setFacturaDate(new Date());
         EntityManagerGeneric.et.begin();
        
                  
                   EntityManagerGeneric.em.persist(factura);
                 

        
         EntityManagerGeneric.et.commit();
          }catch(Exception e)
         {
             if(EntityManagerGeneric.et.isActive())
             {
                 EntityManagerGeneric.et.rollback();
             }
              factura.setNo_factura(ConsecutivoFacturaController.getNoConsecutivo2(0));
                   factura.setFacturaDate(new Date());
           EntityManagerGeneric.et.begin();
        
                  
                   EntityManagerGeneric.em.persist(factura);
            EntityManagerGeneric.et.commit();
         }
       
         
      return factura; 
      
    }
    public Factura guardarFactura(Factura factura) 
    {
         //buscar cliente por cedula
        if(EntityManagerGeneric.et.isActive()){
            EntityManagerGeneric.et.rollback();
        }
            Terceros tercero;
        try{
             
              tercero=(Terceros)EntityManagerGeneric.em.createQuery("select t from Terceros t where t.no_ident=:ident")
                                     .setParameter("ident", factura.getCustomer().getNo_ident())
                                     .setMaxResults(1)
                                     .getSingleResult();
              tercero.setDir1(factura.getCustomer().getDir1());
              tercero.setCelular(factura.getCustomer().getCelular());
              tercero. setEmail(factura.getCustomer().getEmail());
              factura.setCustomer(tercero);
              
        }catch(Exception e)
        {   
            tercero=null;
             
        }
          
      EntityManagerGeneric.et.begin();
      factura=EntityManagerGeneric.em.merge(factura);
      EntityManagerGeneric.et.commit();
      
     
        return factura;
    }   
public List<Factura> getFacturasACredito( String search)
{
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select f from Factura f where (lower(f.customer.nombre) like :search " 
                                             + "  or lower(f.customer.no_ident)like :search) "
                                             + "  and f.credito=true " 
                                             + "  and f.totalAmount > f.valor_abonos "
                                             + "  order by f.facturaDate" )
                                             .setParameter("search", searchpattern)
                                            .getResultList();
}

public Factura FNext(Factura FacturaPrevious,int id)
{
    Long Nofactura=FacturaPrevious.getNo_factura()+Long.valueOf(1);
    
    Factura factura;
    try
    {
          factura=(Factura)getEntityManager().createQuery("select f from Factura f where f.no_factura=:ln")
                                       .setParameter("ln",Nofactura)
                                       .getSingleResult();
              
     
    }catch(Exception e)
    {
        
       factura=FacturaPrevious;
        if(FacturaPrevious.getNo_factura().equals(Long.valueOf("1")))
        {     
        return factura;
        }
        else
        {
            for(int i=1;i<21;i++)
            {
                try{
                factura=(Factura)getEntityManager().createQuery("select f from Factura f where f.no_factura=:ln")
                                       .setParameter("ln", (Nofactura+Long.valueOf(i)))
                                       .getSingleResult();
                return factura;
                }catch(Exception e2)
                      {
                      
                      }
            }     
        }
    }
    return factura;
}
public Factura FPrevious(Factura FacturaNext, int id)
{
    Long Nofactura=FacturaNext.getNo_factura()-Long.valueOf(id);
   
    Factura factura;
    try
    {
     factura=(Factura)getEntityManager().createQuery("select f from Factura f where f.no_factura=:ln")
                                      .setParameter("ln", Nofactura)
                                       .getSingleResult();
     recordexist=true;
    }catch(Exception e)
    {
    
        factura=FacturaNext;
        if(FacturaNext.getNo_factura().equals(Long.valueOf("1")))
        {     
        return factura;
        }
        else
        {
            for(int i=1;i<21;i++)
            {
                 try{
                factura=(Factura)getEntityManager().createQuery("select f from Factura f where f.no_factura=:ln")
                                       .setParameter("ln", (Nofactura-Long.valueOf(i)))
                                       .getSingleResult();
                return factura;
                }catch(Exception e2)
                      {
                      
                      }
            }     
        }
    }
    return factura;
}

   
    protected EntityManager getEntityManager() 
    {
      return EntityManagerGeneric.em; 
    }
   private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    } 
   
   public List<Factura> getRecords(String search) throws ParseException
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid;
    
         try{
                pid=Long.valueOf(search.trim());
         }catch(Exception e)
         {
          pid=Long.valueOf(0);
         }
        
                        return EntityManagerGeneric.em.createQuery( "select f from Factura f left outer join fetch f.customer as cust "+
                                                                    " where f.no_factura is not null and((lower(cust.nombre) like :search "+ 
                                                                     " or cust.no_ident like :search) or f.no_factura=:pid or cust is null)   order by f.no_factura desc")
                                                                    .setParameter("search", searchpattern)
                                                                    .setParameter("pid", pid)
                                                                    .setMaxResults(50)
                                                                    .getResultList();
        
     
     
    }
     
   public Factura update(Factura factura)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.getRollbackOnly();
       }
       EntityManagerGeneric.et.begin();
       factura=EntityManagerGeneric.em.merge(factura);
       EntityManagerGeneric.et.commit();
       return factura;
   }
   public List<Factura> cartera(String search)
   {
            String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
    
        return EntityManagerGeneric.em.createQuery( "select f from Factura f "+
                                                  " where (lower(f.customer.nombre) like :search or lower(f.customer.no_ident)like :search) and f.credito=true "+ 
                                                    "  and f.totalAmount > f.valor_abonos  order by f.facturaDate ")
                                                   .setParameter("search", searchpattern)
                                                 .getResultList();  
   }
   
   public Factura getlastfactura()
   {
       return (Factura)EntityManagerGeneric.em.createQuery("select f from Factura f where f.id=(select max(f2.id) from Factura f2) ")
                                          .getSingleResult();
   }
}
