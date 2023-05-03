package com.example.flightbookingsystem.Service;

import com.example.flightbookingsystem.Entity.Flight;
import com.example.flightbookingsystem.Entity.FlightSchedule;
import com.example.flightbookingsystem.Entity.Schedule;
import com.example.flightbookingsystem.Repository.FlightRepository;
import com.example.flightbookingsystem.Repository.FlightScheduleRepository;
import com.example.flightbookingsystem.Requests.FlightRequest;
import com.example.flightbookingsystem.Requests.ScheduleRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightScheduleRepository flightScheduleRepository;

    private static List<Flight> flights = new ArrayList<>();
    private static List<Schedule> schedules = new ArrayList<>();


    public List<Flight> viewAllFlights() {
        List<Flight> allFlights = flightRepository.findAll();
        return allFlights;
    }

    public void addFlight(FlightRequest flightRequest) {
        Flight flight = new Flight();
        List<ScheduleRequest> scheduleRequests = flightRequest.getSchedules();
        List<Schedule> schedules = new ArrayList<>();
        flight.setFlightId(flightRequest.getFlightId());
        flight.setAirlineName(flightRequest.getAirlineName());
        flight.setSeatCapacity(flightRequest.getSeatCapacity());
        for (ScheduleRequest scheduleRequest : scheduleRequests) {
            Schedule schedule = new Schedule();
            BeanUtils.copyProperties(scheduleRequest, schedule);
            schedule.setFlight(flight);
            FlightSchedule flightSchedule = new FlightSchedule();
            flightSchedule.setFlight(flight);
            flightSchedule.setSchedule(schedule);
            flightSchedule.setSeatsAvailable(flight.getSeatCapacity());
            flightSchedule.setTotalSeats(flight.getSeatCapacity());
            flightScheduleRepository.save(flightSchedule);

            schedules.add(schedule);
        }
        flight.setSchedules(schedules);
        flightRepository.save(flight);
    }

    public Flight getFlightById(int flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }


    public void removeFlight(int flightId) {
        Flight flightRemove = flightRepository.findById(flightId).get();
        flightRepository.delete(flightRemove);
    }


}

