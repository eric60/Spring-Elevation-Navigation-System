package com.EleNa.controllers;

import com.EleNa.model.BestPathData;
import com.EleNa.model.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.EleNa.repositories.CustomerRepository;


@RestController
public class MainController {
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 100)
    @PostMapping("/submit")
    public double[][] processForm(@RequestBody FormData formdata){
        System.out.printf("elevation: %s\nwithinX: %d\nstarting: %s\ndestination: %s",
                formdata.getElevationPref(), formdata.getWithinX(), formdata.getStarting(), formdata.getDestination());

        BestPathData bestPathData = new BestPathData();
        double[][] bestPath =  bestPathData.calculateRoute(formdata);
        return bestPath;
    }



}
