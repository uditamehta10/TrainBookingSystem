package com.example.trainbookingsystem.Service;

import com.example.trainbookingsystem.Entity.TrainSchedule;
import com.example.trainbookingsystem.Repository.TrainScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
@Slf4j
@Service
public class TrainScheduleService {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    TrainScheduleRepository trainScheduleRepository;

    public int getSeatsAvailable(int scheduleId, int trainId) {
        return trainScheduleRepository.findAvailableSeats(scheduleId, trainId);

    }


    @Transactional
    public void updateAvailableSeats(int scheduleId, int trainId, int numOfSeats) {

            TrainSchedule trainSchedule = trainScheduleRepository.getTrainScheduleByTrainAndSchedule(scheduleId, trainId);
            //review
            log.info(" current thread {}, object : {}", Thread.currentThread().getName(), trainSchedule.getSeatsAvailable());



           // entityManager.lock(trainSchedule, LockModeType.PESSIMISTIC_WRITE);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int currentSeatsAvailable = trainSchedule.getSeatsAvailable() - numOfSeats;
            trainSchedule.setSeatsAvailable(currentSeatsAvailable);
            trainScheduleRepository.saveAndFlush(trainSchedule);
            log.info(" current thread {}, object : {}", Thread.currentThread().getName(), trainSchedule.getSeatsAvailable());

    }

    public TrainSchedule getTrainScheduleByTrainAndSchedule(int scheduleId, int trainId) {
        return trainScheduleRepository.getTrainScheduleByTrainAndSchedule(scheduleId, trainId);
    }


    @Transactional
    public void resetAvailableSeats(int numOfSeats, int seatsAvailable, int scheduleId, int trainId) {
        TrainSchedule trainSchedule = trainScheduleRepository.getTrainScheduleByTrainAndSchedule(scheduleId, trainId);
        entityManager.lock(trainSchedule, LockModeType.PESSIMISTIC_WRITE);
        int currentSeatsAvailable = trainScheduleRepository.findAvailableSeats(scheduleId, trainId)+numOfSeats;
        trainScheduleRepository.resetAvailableSeats(scheduleId, trainId, currentSeatsAvailable);
        trainScheduleRepository.save(trainSchedule);
    }
}
