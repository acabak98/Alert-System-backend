package com.example.back.backend.controller;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Log;
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

    private Log heloy = new Log();

    @Async
    @Scheduled(fixedRate = 2000)
    public void deneme() throws MalformedURLException, ProtocolException {
        URL mahmut = new URL(heloy.getAdress());
        long milliStart = System.currentTimeMillis();
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) mahmut.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        con.setRequestMethod("GET");



        int status;
        try {
            status = con.getResponseCode();
        } catch (Exception e) {
            status = 300;
        }

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long milliEnd = System.currentTimeMillis();

        long milliTime = milliEnd - milliStart;

        Alert newAlert = new Alert();
        newAlert.setAlert(milliTime);
        urlService.alerting(heloy.getNameOfUrl(), newAlert);
    }

    @PostMapping("/loyloy")
    public void laylay(@RequestParam String name, @RequestParam String url) throws IOException {
        Url urlToPush = new Url();
        urlToPush.setName(name);
        urlToPush.setUrl(url);
        urlService.laylay(urlToPush);
        heloy.setAdress(url);
        heloy.setNameOfUrl(name);
        this.deneme();
    }
}