package com.example.back.backend.controller;
import com.example.back.backend.model.Alert;
import com.example.back.backend.services.AlertService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/addalert")
    public void addAlert(@RequestParam String name, @RequestParam String url, @RequestParam Integer controlPeriod) throws IOException {
        alertService.addAlert(name, url, controlPeriod);
    }

    @GetMapping("/selection")
    public List<Alert> selection(){
        return alertService.selection();
    }
}