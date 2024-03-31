package com.example.trainbookingsystem.Controller;

import com.example.trainbookingsystem.Entity.Schedule;
import com.example.trainbookingsystem.Requests.ScheduleRequest;
import com.example.trainbookingsystem.Service.ScheduleService;
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


    @GetMapping("/trains/search")
    public List<Schedule> searchTrainsByDateAndCity(
            @RequestParam("departureCity") String departureCity,
            @RequestParam("arrivalCity") String arrivalCity,
            @RequestParam("date") String date) {

        return scheduleService.searchTrainsByDateAndCity(departureCity, arrivalCity, date);
    }


    @PostMapping("/addSchedule")
    public void addSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest) {
        scheduleService.addSchedule(scheduleRequest);
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public void removeSchedule(@PathVariable("id") int trainId) {
        ScheduleService.removeSchedule(trainId);
    }


}
