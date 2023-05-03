package com.example.flightbookingsystem.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    String arrivalCity;
    String departureCity;

}
