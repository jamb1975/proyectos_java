/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import menu.MenuMain;
import model.Factura;
import model.Producto;

/**
 * Jersey REST client generated for REST resource:CheckNoMesasResource
 * [/checkNoMesas]<br>
 * USAGE:
 * <pre>
 *        CheckMesaJerseyClient client = new CheckMesaJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karol
 */
public class CheckMesaJerseyClient {
    private WebTarget webTarget;
    private Client client;
   
    public CheckMesaJerseyClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(MenuMain.BASE_URI).path("model.facturaitem2");
    }

    public <T> T getXml(Class<T> responseType, String nomesa) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{nomesa}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    public void create_XML(Object requestEntity) throws ClientErrorException {
      Response resp=  webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
      System.out.println(resp.getStatusInfo());
    }

    public Response postXml(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
    }
 
    public void close() {
        client.close();
    }
    
}
