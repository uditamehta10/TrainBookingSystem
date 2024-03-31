package com.example.trainbookingsystem.Controller;

import com.example.trainbookingsystem.Entity.Train;
import com.example.trainbookingsystem.Requests.TrainRequest;
import com.example.trainbookingsystem.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainController {

    private List<Train> trains = new ArrayList<>();

    @Autowired
    TrainService trainService;

    @PostMapping("/addTrain")
    public void addTrain(@RequestBody @Valid TrainRequest trainRequest) {
        trainService.addTrain(trainRequest);
    }

    @GetMapping("/viewTrainById")
    public Train viewTrain(@RequestParam int trainId) {
        return trainService.getTrainById(trainId);
    }

    @DeleteMapping("/deleteTrain/{id}")
    public void removeTrain(@PathVariable("id") int trainId) {
        trainService.removeTrain(trainId);
    }

}
