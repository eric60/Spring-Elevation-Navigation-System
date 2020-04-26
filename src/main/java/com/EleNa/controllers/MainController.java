package com.EleNa.controllers;

import com.EleNa.model.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
public class MainController {
    BestPathService bestPathService;

    @Autowired
    public MainController(BestPathService BPS){
        this.bestPathService = BPS;
    }

    @CrossOrigin(origins = "http://localhost:8080", maxAge = 100)
    @PostMapping("/submit")
    public double[][] processForm(@RequestBody FormData formdata){
        System.out.printf("\nelevation: %s\nwithinX: %d\n", formdata.getElevationPref(), formdata.getWithinX());
        System.out.println(formdata.getStart());
        System.out.println(formdata.getEnd());

        double[][] bestPath =  bestPathService.calculateRoute(formdata);
        System.out.println("\nTrigger best path return");
        System.out.println(Arrays.toString(bestPath));
        return bestPath;
    }



}
