package com.example.trainbookingsystem.Repository;

import com.example.trainbookingsystem.Entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainRepository extends JpaRepository<Train,Integer> {

}
