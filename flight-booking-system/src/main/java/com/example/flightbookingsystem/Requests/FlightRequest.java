package com.example.flightbookingsystem.Requests;

import com.example.flightbookingsystem.Entity.Schedule;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FlightRequest {

    @NotNull
    int flightId;

    @NotNull
    String airlineName;

    @NotNull
    int seatCapacity;

    @NotNull
    List<ScheduleRequest> schedules;
}
