package com.example.trainbookingsystem.Repository;

import com.example.trainbookingsystem.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("Update Booking set is_completed = true where booking_id =:bookingId")
    void updateBooking(int bookingId);

    @Query("Select b from Booking b where b.isCompleted = false and b.state = 'IN_PROGRESS'")
    List<Booking> findByIsCompleteFalse();

}
