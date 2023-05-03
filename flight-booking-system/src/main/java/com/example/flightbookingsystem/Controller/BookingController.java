package com.example.flightbookingsystem.Controller;

import com.example.flightbookingsystem.Entity.Booking;
import com.example.flightbookingsystem.Requests.BookingRequest;
import com.example.flightbookingsystem.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/viewBooking/{bookingId}")
    public Booking viewBooking(@RequestBody int bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/viewAllBookings")
    public List<Booking> viewAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/BookFlight")
    public String bookFlight(@RequestBody @Valid BookingRequest bookingRequest) {
        return bookingService.bookFlight(bookingRequest);
    }

    @PutMapping("/updateBookingStatus")
    public void updateBooking(@RequestParam int bookingId) {
        bookingService.updateBooking(bookingId);
    }
}

