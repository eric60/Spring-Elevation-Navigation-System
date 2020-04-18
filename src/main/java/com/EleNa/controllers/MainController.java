package com.EleNa.controllers;

import com.EleNa.model.FormData;
import org.springframework.web.bind.annotation.*;


@RestController
public class MainController {
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 100)
    @PostMapping("/submit")
    public double[][] processForm(@RequestBody FormData formdata){
        System.out.printf("\nelevation: %s\nwithinX: %d\n", formdata.getElevationPref(), formdata.getWithinX());
        System.out.println(formdata.getStart());
        System.out.println(formdata.getEnd());

        BestPathController bestPathController = new BestPathController();
        double[][] bestPath =  bestPathController.calculateRoute(formdata);
        return bestPath;
    }



}
