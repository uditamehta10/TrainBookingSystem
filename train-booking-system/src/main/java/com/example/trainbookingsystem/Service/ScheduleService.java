package com.example.trainbookingsystem.Service;

import com.example.trainbookingsystem.Entity.Train;
import com.example.trainbookingsystem.Entity.Schedule;
import com.example.trainbookingsystem.Repository.ScheduleRepository;
import com.example.trainbookingsystem.Requests.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;


    private static List<Schedule> schedules = new ArrayList<>();

    public  void addSchedule(ScheduleRequest scheduleRequest) {
        Train train = new Train();
        train.setTrainId(scheduleRequest.getTrainRequest().getTrainId());
        train.setAirlineName(scheduleRequest.getTrainRequest().getAirlineName());
        train.setSeatCapacity(scheduleRequest.getTrainRequest().getSeatCapacity());
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleRequest,schedule);
        schedule.setTrain(train);
        scheduleRepository.save(schedule);
    }

    public static void removeSchedule(int trainId) {
    }


    public  Schedule getScheduleByScheduleId(int scheduleId) {

        return scheduleRepository.findById(scheduleId).get();
    }


    public List<Schedule> searchTrainsByDateAndCity(String departureCity, String arrivalCity, String date) {
        return scheduleRepository.searchTrainsByDateAndCity(departureCity,arrivalCity,date);

    }

}

