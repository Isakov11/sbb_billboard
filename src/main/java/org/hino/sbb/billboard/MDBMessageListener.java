package org.hino.sbb.billboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.jms.MessageListener;
import java.util.LinkedList;
import java.util.List;

@MessageDriven(name = "MyMDB", activationConfig = {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/SBB"),

        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class MDBMessageListener implements MessageListener {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        StationScheduleDTO stationScheduleDTO = new StationScheduleDTO("525","15:43","15:46");
        StationScheduleDTO stationScheduleDTO2 = new StationScheduleDTO("981","18:30","18:35");
        List<StationScheduleDTO> schedList = new LinkedList<>();
        schedList.add(stationScheduleDTO);
        schedList.add(stationScheduleDTO2);
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(schedList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ETFEndpoint.send(jsonString);
    }
}
