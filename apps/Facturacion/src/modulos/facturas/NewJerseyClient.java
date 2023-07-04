/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modulos.facturas;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import menu.MenuMain;

/**
 * Jersey REST client generated for REST resource:ItemsResource [/Items]<br>
 * USAGE:
 * <pre>
 *        NewJerseyClient client = new NewJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karol
 */
public class NewJerseyClient {
    private final WebTarget webTarget;
    private Client client;
   
    public NewJerseyClient() {
       // client.close();
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(MenuMain.BASE_URI).path("Items");
    }

    public <T> T getXml(Class<T> responseType,String noident) throws ClientErrorException {
        WebTarget resource = webTarget;
           resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{noident}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
      //return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).method("getItemResource", responseType);
    }

    public Response postXml(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
