package com.example.trainbookingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int scheduleId;

    @ManyToOne
    @JsonBackReference
    Train train;

    String departureCity;

    String arrivalCity;

    String startTime;

    String endTime;

    String trainDuration;

    @Column(name = "schedule_date")
    String date;
    //Can take fare as BigDecimal
    int fare;

    @Column
    @CreationTimestamp
    Date createdAt;

    @Column
    @UpdateTimestamp
    Date updatedAt;


}

