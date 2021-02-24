package org.hino.sbb.billboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.jms.MessageListener;

@MessageDriven(name = "MyMDB", activationConfig = {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/SBB"),

        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class MDBMessageListener implements MessageListener {
    private static final Logger logger = Logger.getLogger(MDBMessageListener.class);
    private ObjectMapper mapper = new ObjectMapper();

    @EJB
    RsClient rsClient;

    @EJB
    ETFEndpoint eTFEndpoint;

    @Override
    public void onMessage(Message message) {
        String jsonString = rsClient.getDate();
        logger.info("JMS message received");
        eTFEndpoint.send(jsonString);
    }
}
