package com.example.flightbookingsystem.Repository;

import com.example.flightbookingsystem.Entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Integer> {

    @Modifying
    @Query("Update FlightSchedule set seats_available =:currentSeatsAvailable  where schedule_schedule_id =:scheduleId and flight_flight_id=:flightId ")
    void updateAvailableSeats(int scheduleId, int flightId, int currentSeatsAvailable);

    @Query("Select seatsAvailable from FlightSchedule where schedule_schedule_id = :scheduleId and flight_flight_id=:flightId")
    int findAvailableSeats(int scheduleId, int flightId);

    @Query("Select fs from FlightSchedule fs where schedule_schedule_id = :scheduleId and flight_flight_id=:flightId")
    FlightSchedule getFlightScheduleByFlightAndSchedule(int scheduleId, int flightId);

    @Modifying
    @Query("Update FlightSchedule set seats_available =:currentSeatsAvailable  where schedule_schedule_id =:scheduleId and flight_flight_id=:flightId ")
    void resetAvailableSeats(int scheduleId, int flightId, int currentSeatsAvailable);
}
