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

    @Scheduled(fixedRate = 1000)
    public void deneme() throws MalformedURLException, ProtocolException {

        List<Url> liste = urlRepository.findAll();

        for (Url item : liste) {
            if (item.getRemaining()==0) {
                Alert newAlert = new Alert();
                newAlert.setTimeDifference(System.currentTimeMillis());
                item.setTime(System.currentTimeMillis());
                item.setRemaining(item.getPeriod());
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
                newAlert.setAlert(status);
                this.alerting(item.getName(), newAlert);
            }
            else {
                item.setRemaining(item.getRemaining()-1);
                urlRepository.save(item);
            }
        }
    }

    public void alerting(final String givenName, Alert yenAlert){
        Url urlToAdd = urlRepository.findByName(givenName);
        if (urlToAdd != null) {
            urlToAdd.getAlert().add(yenAlert);
            urlRepository.save(urlToAdd);
        }
    }
}
