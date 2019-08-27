package com.example.back.backend.services;

import com.example.back.backend.model.Response;
import com.example.back.backend.model.Url;
import com.example.back.backend.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void connection(Url item) throws ProtocolException, MalformedURLException {
        Response newResponse = new Response();
        newResponse.setTimeDifference(System.currentTimeMillis());
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
        if(status==200) {
            newResponse.setAlert(10);
            this.alerting(item.getName(), newResponse);
        }
        else {
            newResponse.setAlert(0);
            this.alerting(item.getName(), newResponse);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void deneme() throws MalformedURLException, ProtocolException {

        List<Url> liste = urlRepository.findAll();

        for (Url item : liste) {
            if (item.getRemaining()==0) {
                connection(item);
            }
            else {
                item.setRemaining(item.getRemaining()-1);
                urlRepository.save(item);
            }
        }
    }

    public void alerting(final String givenName, Response yenResponse){
        Url urlToAdd = urlRepository.findByName(givenName);
        if (urlToAdd != null) {
            urlToAdd.getResponse().add(yenResponse);
            urlRepository.save(urlToAdd);
        }
    }
}
