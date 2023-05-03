package com.example.flightbookingsystem.Controller;

import com.example.flightbookingsystem.Entity.Flight;
import com.example.flightbookingsystem.Requests.FlightRequest;
import com.example.flightbookingsystem.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {

    private List<Flight> flights = new ArrayList<>();

    @Autowired
    FlightService flightService;

    @PostMapping("/addFlight")
    public void addFlight(@RequestBody @Valid FlightRequest flightRequest) {
        flightService.addFlight(flightRequest);
    }

    @GetMapping("/viewFlightById")
    public Flight viewFlight(@RequestParam int flightId) {
        return flightService.getFlightById(flightId);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public void removeFlight(@PathVariable("id") int flightId) {
        flightService.removeFlight(flightId);
    }

}
