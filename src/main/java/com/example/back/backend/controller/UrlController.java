package com.example.back.backend.controller;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Url;
import com.example.back.backend.services.UrlService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@RestController
@CrossOrigin("*")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }



    @PostMapping("/loyloy")
    public void laylay(@RequestParam String name, @RequestParam String url) throws IOException {
        Url urlToPush = new Url();
        urlToPush.setName(name);
        urlToPush.setUrl(url);
        urlToPush.setTime(System.currentTimeMillis());
        urlService.laylay(urlToPush);
    }
}