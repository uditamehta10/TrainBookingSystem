package com.example.flightbookingsystem.Repository;

import com.example.flightbookingsystem.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    @Query("SELECT s FROM Schedule s WHERE s.date = :date AND s.departureCity = :departureCity AND s.arrivalCity = :arrivalCity")
    List<Schedule> searchFlightsByDateAndCity(String departureCity, String arrivalCity, String date);
}
