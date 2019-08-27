package com.example.back.backend.controller;

import com.example.back.backend.model.Url;
import com.example.back.backend.services.UrlService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }



    @PostMapping("/loyloy")
    public void laylay(@RequestParam String name, @RequestParam String url, @RequestParam Integer controlPeriod) throws IOException {
        Url urlToPush = new Url();
        urlToPush.setName(name);
        urlToPush.setUrl(url);
        urlToPush.setPeriod(controlPeriod);
        urlToPush.setRemaining(0);
        urlToPush.setTime(System.currentTimeMillis());
        urlService.laylay(urlToPush);
    }
}