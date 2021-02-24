package org.hino.sbb.billboard;

import org.apache.log4j.Logger;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.lang.Thread.sleep;

@Singleton
public class RsClient {
    private static final Logger logger = Logger.getLogger(RsClient.class);

    private Client client = ClientBuilder.newClient();
    private WebTarget myResource = client.target("http://localhost:8080/admin/stations/api/1");
    private int counter = 0;
    private final int MAX_CONNECT_TRY = 10;

    public String getDate () {
        String req= "";
        try {
            Response response = myResource.request(MediaType.TEXT_PLAIN).get();
            if (response.getStatusInfo() == Response.Status.OK){
                req = response.readEntity(String.class);
                counter = 0;
            }
            else{
                if (counter < MAX_CONNECT_TRY){
                    try {
                        sleep(5000);
                        counter++;
                        getDate();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }catch (WebApplicationException | ResponseProcessingException ex) {
            try {
                if (counter < MAX_CONNECT_TRY ) {
                    sleep(5000);
                    counter++;
                    getDate();
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return req;
    }

    @PreDestroy
    private void destroy(){
        client.close();
    }
}