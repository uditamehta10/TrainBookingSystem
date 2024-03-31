package com.example.trainbookingsystem.Entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    @OneToOne
    Schedule schedule;

    @OneToOne
    Train train;

    private int numOfSeats;

    private int totalCost;

    Boolean isCompleted;

    @Column
    @CreationTimestamp
    Date createdAt;

    @Column
    @UpdateTimestamp
    Date updatedAt;

    private String state;


}
