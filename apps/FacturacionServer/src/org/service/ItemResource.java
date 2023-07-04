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
import model.Terceros;

/**
 * REST Web Service
 *
 * @author karol
 */
public class ItemResource {

    private String id;

    /**
     * Creates a new instance of ItemResource
     */
    private ItemResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the ItemResource
     */
    public static ItemResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ItemResource class.
        return new ItemResource(id);
    }

    /**
     * Retrieves representation of an instance of service.ItemResource
     * @return an instance of model.Terceros
     */
    @GET
    @Produces("application/xml")
    public Terceros getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ItemResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(Terceros content) {
    }

    /**
     * DELETE method for resource ItemResource
     */
    @DELETE
    public void delete() {
    }
}
