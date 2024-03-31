package com.example.trainbookingsystem.Requests;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class BookingRequest {
    @NotNull
    int scheduleId;

    @NotNull
    int numOfSeats;
}
