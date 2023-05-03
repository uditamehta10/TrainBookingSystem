package com.example.flightbookingsystem.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Flight")
public class Flight {


    @Id
    int flightId;

    String airlineName;
    int seatCapacity;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Schedule> schedules;

    @CreationTimestamp
    Date createdAt;

    @Column
    @UpdateTimestamp
    Date updatedAt;

}
