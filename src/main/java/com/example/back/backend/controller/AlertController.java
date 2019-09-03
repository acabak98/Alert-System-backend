package com.example.back.backend.controller;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Response;
import com.example.back.backend.services.AlertService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OrderBy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }



    @PostMapping("/loyloy")
    public void laylay(@RequestParam String name, @RequestParam String url, @RequestParam Integer controlPeriod) throws IOException {
        Alert alertToPush = new Alert();
        alertToPush.setName(name);
        alertToPush.setUrl(url);
        alertToPush.setPeriod(controlPeriod);
        alertToPush.setRemaining(0);
        alertToPush.setTime(System.currentTimeMillis());
        alertService.laylay(alertToPush);
    }

    @GetMapping("/selection")
    public List<Alert> selection(){
        return alertService.selection();
    }

    @GetMapping("/graph")
    public List<Integer> graph(@RequestParam String name){
        List<Integer> dataResponse = new ArrayList<Integer>();
        Alert alertToGraph = alertService.takingGraph(name);
        for (Response singleResponse : alertToGraph.getResponse()) {
            dataResponse.add(singleResponse.getSuccess());
        }
        return dataResponse;
    }

    @GetMapping("/graph1")
    public List<Long> graph1(@RequestParam String name){
        List<Long> dataResponse = new ArrayList<Long>();
        /*Alert alertToGraph = alertService.takingGraph(name);
        for (Response singleResponse : alertToGraph.getResponse()) {
            dataResponse.add(singleResponse.getTimeDifference());
        }*/
        List<Response> hop = alertService.takingGraph1(name);
        for(Response single : hop) {
            dataResponse.add(single.getTimeDifference());
        }
        return dataResponse;
    }
}