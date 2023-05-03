package com.example.flightbookingsystem.Service;

import com.example.flightbookingsystem.Entity.Flight;
import com.example.flightbookingsystem.Entity.Schedule;
import com.example.flightbookingsystem.Repository.FlightRepository;
import com.example.flightbookingsystem.Repository.ScheduleRepository;
import com.example.flightbookingsystem.Requests.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;


    private static List<Schedule> schedules = new ArrayList<>();

    public  void addSchedule(ScheduleRequest scheduleRequest) {
        Flight flight = new Flight();
        flight.setFlightId(scheduleRequest.getFlightRequest().getFlightId());
        flight.setAirlineName(scheduleRequest.getFlightRequest().getAirlineName());
        flight.setSeatCapacity(scheduleRequest.getFlightRequest().getSeatCapacity());
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleRequest,schedule);
        schedule.setFlight(flight);
        scheduleRepository.save(schedule);
    }

    public static void removeSchedule(int flightId) {
    }


    public  Schedule getScheduleByScheduleId(int scheduleId) {

        return scheduleRepository.findById(scheduleId).get();
    }


    public List<Schedule> searchFlightsByDateAndCity(String departureCity, String arrivalCity, String date) {
        return scheduleRepository.searchFlightsByDateAndCity(departureCity,arrivalCity,date);

    }

}

