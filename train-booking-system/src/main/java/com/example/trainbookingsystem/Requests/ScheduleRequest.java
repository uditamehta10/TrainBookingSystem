package com.example.trainbookingsystem.Requests;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ScheduleRequest {

    @NotNull
    TrainRequest trainRequest;
    @NotNull
    String departureCity;
    @NotNull
    String arrivalCity;
    @NotNull
    String startTime;
    @NotNull
    String endTime;
    @NotNull
    String trainDuration;
    @NotNull
    String date;
    @NotNull
    int fare;
}
