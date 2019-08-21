package com.example.back.backend.services;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Url;
import com.example.back.backend.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public void laylay(final Url url) throws MalformedURLException, ProtocolException {
        urlRepository.save(url);
        this.deneme();
    }

    @Async
    @Scheduled(fixedRate = 500)
    public void deneme() throws MalformedURLException, ProtocolException {

        List<Url> liste = urlRepository.findAll();

        for (int i=0; i < liste.size();i++) {
            Url item = liste.get(i);
            if (System.currentTimeMillis() - item.getTime() < 4000 && System.currentTimeMillis() - item.getTime() >2000) {

                Alert newAlert = new Alert();
                newAlert.setCurrentTime(System.currentTimeMillis());
                newAlert.setTimeDifference(System.currentTimeMillis() - item.getTime());
                item.setTime(System.currentTimeMillis());
                urlRepository.save(item);
                URL mahmut = new URL(item.getUrl());
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


                newAlert.setResponse(status);

                this.alerting(item.getName(), newAlert);
            }
        }

        /*URL mahmut = new URL(heloy.getAdress());
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
        newAlert.setAlert(Long.valueOf(status));
        this.alerting(heloy.getNameOfUrl(), newAlert);*/
    }

    public void alerting(final String givenName, Alert yenAlert){
        Url urlToAdd = urlRepository.findByName(givenName);
        if (urlToAdd != null) {
            urlToAdd.getAlert().add(yenAlert);
            urlRepository.save(urlToAdd);
        }
    }
}
