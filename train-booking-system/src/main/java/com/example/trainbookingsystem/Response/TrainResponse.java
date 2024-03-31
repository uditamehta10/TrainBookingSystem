package com.example.trainbookingsystem.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainResponse {
    String arrivalCity;
    String departureCity;

}
