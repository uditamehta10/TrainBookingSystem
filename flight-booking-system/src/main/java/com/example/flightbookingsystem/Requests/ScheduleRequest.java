package com.example.flightbookingsystem.Requests;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ScheduleRequest {

    @NotNull
    FlightRequest flightRequest;
    @NotNull
    String departureCity;
    @NotNull
    String arrivalCity;
    @NotNull
    String startTime;
    @NotNull
    String endTime;
    @NotNull
    String flightDuration;
    @NotNull
    String date;
    @NotNull
    int fare;
}
