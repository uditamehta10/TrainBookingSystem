package com.example.trainbookingsystem.Controller;

import com.example.trainbookingsystem.Entity.Booking;
import com.example.trainbookingsystem.Requests.BookingRequest;
import com.example.trainbookingsystem.Service.BookingService;
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

    @PostMapping("/BookTrain")
    public String bookTrain(@RequestBody @Valid BookingRequest bookingRequest) {
        return bookingService.bookTrain(bookingRequest);
    }

    @PutMapping("/updateBookingStatus")
    public void updateBooking(@RequestParam int bookingId) {
        bookingService.updateBooking(bookingId);
    }
}

