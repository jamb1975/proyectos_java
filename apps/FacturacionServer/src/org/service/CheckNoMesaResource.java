/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author karol
 */
public class CheckNoMesaResource {

    private String name;

    /**
     * Creates a new instance of CheckNoMesaResource
     */
    private CheckNoMesaResource(String name) {
        this.name = name;
    }

    /**
     * Get instance of the CheckNoMesaResource
     */
    public static CheckNoMesaResource getInstance(String name) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of CheckNoMesaResource class.
        return new CheckNoMesaResource(name);
    }

    /**
     * Retrieves representation of an instance of service.CheckNoMesaResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of CheckNoMesaResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource CheckNoMesaResource
     */
    @DELETE
    public void delete() {
    }
}




 
