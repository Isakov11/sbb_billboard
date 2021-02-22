package org.hino.sbb.billboard;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private ObjectMapper mapper = new ObjectMapper();

    @EJB
    RsClient rsClient;

    @Override
    public void onMessage(Message message) {
        String jsonString = rsClient.getDate();
        ETFEndpoint.send(jsonString);
    }
}
