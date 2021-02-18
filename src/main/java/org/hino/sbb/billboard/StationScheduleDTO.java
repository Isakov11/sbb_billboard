package org.hino.sbb.billboard;

import java.time.LocalDateTime;

public class StationScheduleDTO {

    private String trainNumber;

    private String arrivalTime;

    private String departureTime;

    public StationScheduleDTO() {
    }

    public StationScheduleDTO(String trainNumber, String arrivalTime, String departureTime) {
        this.trainNumber = trainNumber;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
