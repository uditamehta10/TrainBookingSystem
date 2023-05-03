package com.example.flightbookingsystem.Service;

import com.example.flightbookingsystem.Entity.Booking;
import com.example.flightbookingsystem.Entity.Flight;
import com.example.flightbookingsystem.Entity.FlightSchedule;
import com.example.flightbookingsystem.Entity.Schedule;
import com.example.flightbookingsystem.Enum.StateEnum;
import com.example.flightbookingsystem.Repository.BookingRepository;
import com.example.flightbookingsystem.Repository.FlightScheduleRepository;
import com.example.flightbookingsystem.Requests.BookingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    FlightScheduleService flightScheduleService;

    @Autowired
    FlightScheduleRepository flightScheduleRepository;


    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    public String bookFlight(BookingRequest bookingRequest) {
        Schedule schedule = scheduleService.getScheduleByScheduleId(bookingRequest.getScheduleId());
      Flight flight = schedule.getFlight();
        if (bookingRequest.getNumOfSeats() > flightScheduleService.getSeatsAvailable(schedule.getScheduleId(), flight.getFlightId())) {
            Booking booking = new Booking();
            booking.setState(StateEnum.FAILED.toString());
            booking.setFlight(flight);
            booking.setSchedule(schedule);
            booking.setNumOfSeats(bookingRequest.getNumOfSeats());
            booking.setTotalCost(schedule.getFare() * bookingRequest.getNumOfSeats());
            booking.setIsCompleted(true);
            bookingRepository.save(booking);
            return "Not enough seats available. The number of seats available are : " + flightScheduleService.getSeatsAvailable(schedule.getScheduleId(), flight.getFlightId());
        } else {
            Booking booking = new Booking();
            booking.setState(StateEnum.IN_PROGRESS.toString());
            booking.setFlight(flight);
            booking.setSchedule(schedule);
            booking.setNumOfSeats(bookingRequest.getNumOfSeats());
            booking.setTotalCost(schedule.getFare() * bookingRequest.getNumOfSeats());
            booking.setIsCompleted(false);
            bookingRepository.save(booking);
            flightScheduleService.updateAvailableSeats(schedule.getScheduleId(), flight.getFlightId(), booking.getNumOfSeats());

            if (flightScheduleService.getSeatsAvailable(schedule.getScheduleId(), flight.getFlightId()) < 0) {
                booking.setState(StateEnum.FAILED.toString());
                booking.setIsCompleted(true);
                flightScheduleService.resetAvailableSeats(booking.getNumOfSeats(), flightScheduleService.getSeatsAvailable(schedule.getScheduleId(), flight.getFlightId()), schedule.getScheduleId(), flight.getFlightId());
            }
        }
        return "Booking in progress";
    }

    public String updateBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setIsCompleted(true);
        booking.setState(StateEnum.SUCCESS.toString());
        bookingRepository.save(booking);
        return "Your booking has been confirmed. Your booking id is : " + booking.getBookingId();
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void updateSeatsFromIncompleteBookings() {
        List<Booking> incompleteBookings = bookingRepository.findByIsCompleteFalse();
        // update available seats for each booking
        int numSeats = 0;
        for (Booking booking : incompleteBookings) {
       //     log.info("Scheduler started for booking id : {}", booking.getBookingId());
         //   log.info("booking created : {}", booking.getCreatedAt());

            Date bookingStartTime = booking.getCreatedAt();
            Date currentTime = new Date(System.currentTimeMillis());
            long duration = currentTime.getTime() - bookingStartTime.getTime();
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            if (diffInMinutes > 4) {
            numSeats = booking.getNumOfSeats();
          //     log.info("num of seats for booking id : {}", booking.getBookingId());
                //query state in_progress
                Schedule schedule = booking.getSchedule();
                FlightSchedule flightSchedule = flightScheduleService.getFlightScheduleByFlightAndSchedule(schedule.getScheduleId(), schedule.getFlight().getFlightId());
                int seatsAvailable = flightSchedule.getSeatsAvailable();
           //    log.info("seats available {}", seatsAvailable);
                int newSeatsAvailable = seatsAvailable + numSeats;
            //   log.info("new seats available {}", newSeatsAvailable);

                if (newSeatsAvailable > flightSchedule.getTotalSeats()) {
               //     log.info("Scheduler loop broken for booking id : {}", booking.getBookingId());
                 //   log.info("totalseats for booking id : {}", flightSchedule.getTotalSeats());
                    booking.setState(StateEnum.FAILED.toString());
                    bookingRepository.save(booking);
                    break;

                }

                flightSchedule.setSeatsAvailable(newSeatsAvailable);
                flightScheduleRepository.save(flightSchedule);
                booking.setState(StateEnum.FAILED.toString());
                booking.setIsCompleted(true);
                bookingRepository.save(booking);
          //      log.info("Booking updated for : {}", booking.getBookingId());
            }

        }
    }
}

