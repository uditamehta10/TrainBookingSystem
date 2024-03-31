package com.example.trainbookingsystem.Requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TrainRequest {

    @NotNull
    int trainId;

    @NotNull
    String airlineName;

    @NotNull
    int seatCapacity;

    @NotNull
    List<ScheduleRequest> schedules;
}
