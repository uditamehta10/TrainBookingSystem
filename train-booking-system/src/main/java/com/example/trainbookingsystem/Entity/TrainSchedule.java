package com.example.trainbookingsystem.Entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class TrainSchedule {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;


    @OneToOne(cascade = CascadeType.ALL)
    Train train;

    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;

    int seatsAvailable;

    int totalSeats;

    @Column
    @CreationTimestamp
    Date createdAt;

    @Column
    @UpdateTimestamp
    Date updatedAt;

}

