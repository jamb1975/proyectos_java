/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.service;


import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import model.Terceros;
//import org.jboss.logging.Param;

/**
 * REST Web Service
 *
 * @author karol
 */

@Path("/Items")
public class ItemsResource extends AbstractFacade<Terceros>  {
   @PersistenceContext(unitName = "FacturacionServerPU",synchronization = javax.persistence.SynchronizationType.SYNCHRONIZED)
      private EntityManager em;
private static final String FIND_TERCERO_QUERY = 
            "select "
                + "t "
                + "from Terceros t "
                + "where t.no_ident=:noident";
   
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ItemsResource
     */
    public ItemsResource() {
        super(Terceros.class);
    }

    /**
     * Retrieves representation of an instance of service.ItemsResource
     * @return an instance of model.Terceros
     */
    @GET
    @Path("{noident}")
    @Produces("application/xml")
    public Terceros getXml(@PathParam("noident") String noident) {
        Terceros t=new Terceros();
        Query q = em.createQuery(FIND_TERCERO_QUERY);
       
        Parameter<String> p1 = q.getParameter("noident", String.class);
        q.setParameter(p1, noident);
        t = (Terceros)q.getSingleResult();
               //  em.refresh((Terceros)resultList, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
         t.setNombre("Prueba");
        return (Terceros)t;
    }

    /**
     * POST method for creating an instance of ItemResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postXml(Terceros content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    
    public <T> T getItemResource(Class<T> responseType) {
               Terceros t=new Terceros();
               t.setNombre("JAVIER MURCIA");
        return(T) t;   //throw new UnsupportedOperationException();
  
    }

   
    /*protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

   protected EntityManager getEntityManager() {
        return em;
     }
}
