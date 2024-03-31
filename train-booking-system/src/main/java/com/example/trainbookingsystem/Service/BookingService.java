package com.example.trainbookingsystem.Service;

import com.example.trainbookingsystem.Entity.Booking;
import com.example.trainbookingsystem.Entity.Train;
import com.example.trainbookingsystem.Entity.TrainSchedule;
import com.example.trainbookingsystem.Entity.Schedule;
import com.example.trainbookingsystem.Enum.StateEnum;
import com.example.trainbookingsystem.Repository.BookingRepository;
import com.example.trainbookingsystem.Repository.TrainScheduleRepository;
import com.example.trainbookingsystem.Requests.BookingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    TrainScheduleService trainScheduleService;

    @Autowired
    TrainScheduleRepository trainScheduleRepository;


    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    public String bookTrain(BookingRequest bookingRequest) {
        Schedule schedule = scheduleService.getScheduleByScheduleId(bookingRequest.getScheduleId());
      Train train = schedule.getTrain();
        if (bookingRequest.getNumOfSeats() > trainScheduleService.getSeatsAvailable(schedule.getScheduleId(), train.getTrainId())) {
            Booking booking = new Booking();
            booking.setState(StateEnum.FAILED.toString());
            booking.setTrain(train);
            booking.setSchedule(schedule);
            booking.setNumOfSeats(bookingRequest.getNumOfSeats());
            booking.setTotalCost(schedule.getFare() * bookingRequest.getNumOfSeats());
            booking.setIsCompleted(true);
            bookingRepository.save(booking);
            return "Not enough seats available. The number of seats available are : " + trainScheduleService.getSeatsAvailable(schedule.getScheduleId(), train.getTrainId());
        } else {
            Booking booking = new Booking();
            booking.setState(StateEnum.IN_PROGRESS.toString());
            booking.setTrain(train);
            booking.setSchedule(schedule);
            booking.setNumOfSeats(bookingRequest.getNumOfSeats());
            booking.setTotalCost(schedule.getFare() * bookingRequest.getNumOfSeats());
            booking.setIsCompleted(false);
            bookingRepository.save(booking);
            trainScheduleService.updateAvailableSeats(schedule.getScheduleId(), train.getTrainId(), booking.getNumOfSeats());

            if (trainScheduleService.getSeatsAvailable(schedule.getScheduleId(), train.getTrainId()) < 0) {
                booking.setState(StateEnum.FAILED.toString());
                booking.setIsCompleted(true);
                trainScheduleService.resetAvailableSeats(booking.getNumOfSeats(), trainScheduleService.getSeatsAvailable(schedule.getScheduleId(), train.getTrainId()), schedule.getScheduleId(), train.getTrainId());
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
                TrainSchedule trainSchedule = trainScheduleService.getTrainScheduleByTrainAndSchedule(schedule.getScheduleId(), schedule.getTrain().getTrainId());
                int seatsAvailable = trainSchedule.getSeatsAvailable();
           //    log.info("seats available {}", seatsAvailable);
                int newSeatsAvailable = seatsAvailable + numSeats;
            //   log.info("new seats available {}", newSeatsAvailable);

                if (newSeatsAvailable > trainSchedule.getTotalSeats()) {
               //     log.info("Scheduler loop broken for booking id : {}", booking.getBookingId());
                 //   log.info("totalseats for booking id : {}", trainSchedule.getTotalSeats());
                    booking.setState(StateEnum.FAILED.toString());
                    bookingRepository.save(booking);
                    break;

                }

                trainSchedule.setSeatsAvailable(newSeatsAvailable);
                trainScheduleRepository.save(trainSchedule);
                booking.setState(StateEnum.FAILED.toString());
                booking.setIsCompleted(true);
                bookingRepository.save(booking);
          //      log.info("Booking updated for : {}", booking.getBookingId());
            }

        }
    }
}

