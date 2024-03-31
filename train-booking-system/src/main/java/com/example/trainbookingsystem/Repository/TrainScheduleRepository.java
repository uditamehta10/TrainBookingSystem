package com.example.trainbookingsystem.Repository;

import com.example.trainbookingsystem.Entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule,Integer> {

    @Modifying
    @Query("Update TrainSchedule set seats_available =:currentSeatsAvailable  where schedule_schedule_id =:scheduleId and train_train_id=:trainId ")
    void updateAvailableSeats(int scheduleId, int trainId, int currentSeatsAvailable);

    @Query("Select seatsAvailable from TrainSchedule where schedule_schedule_id = :scheduleId and train_train_id=:trainId")
    int findAvailableSeats(int scheduleId, int trainId);

    @Query("Select fs from TrainSchedule fs where schedule_schedule_id = :scheduleId and train_train_id=:trainId")
    TrainSchedule getTrainScheduleByTrainAndSchedule(int scheduleId, int trainId);

    @Modifying
    @Query("Update TrainSchedule set seats_available =:currentSeatsAvailable  where schedule_schedule_id =:scheduleId and train_train_id=:trainId ")
    void resetAvailableSeats(int scheduleId, int trainId, int currentSeatsAvailable);
}
