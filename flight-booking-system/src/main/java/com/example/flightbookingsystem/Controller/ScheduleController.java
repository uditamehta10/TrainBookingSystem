package com.example.flightbookingsystem.Controller;

import com.example.flightbookingsystem.Entity.Schedule;
import com.example.flightbookingsystem.Requests.ScheduleRequest;
import com.example.flightbookingsystem.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;


    @GetMapping("/flights/search")
    public List<Schedule> searchFlightsByDateAndCity(
            @RequestParam("departureCity") String departureCity,
            @RequestParam("arrivalCity") String arrivalCity,
            @RequestParam("date") String date) {

        return scheduleService.searchFlightsByDateAndCity(departureCity, arrivalCity, date);
    }


    @PostMapping("/addSchedule")
    public void addSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest) {
        scheduleService.addSchedule(scheduleRequest);
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public void removeSchedule(@PathVariable("id") int flightId) {
        ScheduleService.removeSchedule(flightId);
    }


}
