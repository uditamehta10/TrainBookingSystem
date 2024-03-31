package com.example.trainbookingsystem.Service;

import com.example.trainbookingsystem.Entity.Train;
import com.example.trainbookingsystem.Entity.TrainSchedule;
import com.example.trainbookingsystem.Entity.Schedule;
import com.example.trainbookingsystem.Repository.TrainRepository;
import com.example.trainbookingsystem.Repository.TrainScheduleRepository;
import com.example.trainbookingsystem.Requests.TrainRequest;
import com.example.trainbookingsystem.Requests.ScheduleRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TrainScheduleRepository trainScheduleRepository;

    private static List<Train> trains = new ArrayList<>();
    private static List<Schedule> schedules = new ArrayList<>();


    public List<Train> viewAllTrains() {
        List<Train> allTrains = trainRepository.findAll();
        return allTrains;
    }

    public void addTrain(TrainRequest trainRequest) {
        Train train = new Train();
        List<ScheduleRequest> scheduleRequests = trainRequest.getSchedules();
        List<Schedule> schedules = new ArrayList<>();
        train.setTrainId(trainRequest.getTrainId());
        train.setAirlineName(trainRequest.getAirlineName());
        train.setSeatCapacity(trainRequest.getSeatCapacity());
        for (ScheduleRequest scheduleRequest : scheduleRequests) {
            Schedule schedule = new Schedule();
            BeanUtils.copyProperties(scheduleRequest, schedule);
            schedule.setTrain(train);
            TrainSchedule trainSchedule = new TrainSchedule();
            trainSchedule.setTrain(train);
            trainSchedule.setSchedule(schedule);
            trainSchedule.setSeatsAvailable(train.getSeatCapacity());
            trainSchedule.setTotalSeats(train.getSeatCapacity());
            trainScheduleRepository.save(trainSchedule);

            schedules.add(schedule);
        }
        train.setSchedules(schedules);
        trainRepository.save(train);
    }

    public Train getTrainById(int trainId) {
        return trainRepository.findById(trainId).orElse(null);
    }


    public void removeTrain(int trainId) {
        Train trainRemove = trainRepository.findById(trainId).get();
        trainRepository.delete(trainRemove);
    }


}

