package com.example.flightbookingsystem.Service;

import com.example.flightbookingsystem.Entity.FlightSchedule;
import com.example.flightbookingsystem.Repository.FlightScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
@Slf4j
@Service
public class FlightScheduleService {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    FlightScheduleRepository flightScheduleRepository;

    public int getSeatsAvailable(int scheduleId, int flightId) {
        return flightScheduleRepository.findAvailableSeats(scheduleId, flightId);

    }


    @Transactional
    public void updateAvailableSeats(int scheduleId, int flightId, int numOfSeats) {

            FlightSchedule flightSchedule = flightScheduleRepository.getFlightScheduleByFlightAndSchedule(scheduleId, flightId);
            //review
            log.info(" current thread {}, object : {}", Thread.currentThread().getName(), flightSchedule.getSeatsAvailable());



           // entityManager.lock(flightSchedule, LockModeType.PESSIMISTIC_WRITE);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int currentSeatsAvailable = flightSchedule.getSeatsAvailable() - numOfSeats;
            flightSchedule.setSeatsAvailable(currentSeatsAvailable);
            flightScheduleRepository.saveAndFlush(flightSchedule);
            log.info(" current thread {}, object : {}", Thread.currentThread().getName(), flightSchedule.getSeatsAvailable());

    }

    public FlightSchedule getFlightScheduleByFlightAndSchedule(int scheduleId, int flightId) {
        return flightScheduleRepository.getFlightScheduleByFlightAndSchedule(scheduleId, flightId);
    }


    @Transactional
    public void resetAvailableSeats(int numOfSeats, int seatsAvailable, int scheduleId, int flightId) {
        FlightSchedule flightSchedule = flightScheduleRepository.getFlightScheduleByFlightAndSchedule(scheduleId, flightId);
        entityManager.lock(flightSchedule, LockModeType.PESSIMISTIC_WRITE);
        int currentSeatsAvailable = flightScheduleRepository.findAvailableSeats(scheduleId, flightId)+numOfSeats;
        flightScheduleRepository.resetAvailableSeats(scheduleId, flightId, currentSeatsAvailable);
        flightScheduleRepository.save(flightSchedule);
    }
}
