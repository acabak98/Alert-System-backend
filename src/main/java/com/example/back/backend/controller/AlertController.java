package com.example.back.backend.controller;

import com.example.back.backend.model.Alert;
import com.example.back.backend.services.AlertService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlertController {
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    private final AlertService alertService;

    @PostMapping("/leylimley")
    public Alert check(@RequestBody Alert alert) {
        return alertService.check(alert);
    }

    @GetMapping("/graph")
    public List<Integer> grafik(){
        List<Integer> dataResponse = new ArrayList<Integer>();
        List<Alert> alldata = alertService.grafik();
        Alert tmp;
        for (int a = 0; a< alldata.size(); a++){
            tmp = alldata.get(a);
            dataResponse.add(tmp.getAlert());
        }
        return dataResponse;
    }
}
