package com.example.back.backend.services;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Response;
import com.example.back.backend.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public void laylay(final Alert alert) throws MalformedURLException, ProtocolException {
        alertRepository.save(alert);
        this.deneme();
    }

    @Async
    public void connection(Alert item) throws ProtocolException, MalformedURLException {
        Response newResponse = new Response();
        //newResponse.setTimeDifference(System.currentTimeMillis());
        newResponse.setName(item.getName());
        item.setTime(System.currentTimeMillis());
        item.setRemaining(item.getPeriod());
        alertRepository.save(item);
        long milliStart=System.currentTimeMillis();
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
        long milliEnd=System.currentTimeMillis();
        long milliTime=milliEnd-milliStart;
        newResponse.setTimeDifference(milliTime);
        if(status==200) {
            newResponse.setSuccess(1);
            this.alerting(item.getName(), newResponse);
        }
        else {
            newResponse.setSuccess(0);
            this.alerting(item.getName(), newResponse);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void deneme() throws MalformedURLException, ProtocolException {

        List<Alert> liste = alertRepository.findAll();

        for (Alert item : liste) {
            if (item.getRemaining()==0) {
                connection(item);
            }
            else {
                item.setRemaining(item.getRemaining()-1);
                alertRepository.save(item);
            }
        }
    }

    public void alerting(final String givenName, Response yenResponse){
        Alert alertToAdd = alertRepository.findByName(givenName);
        if (alertToAdd != null) {
            alertToAdd.getResponse().add(yenResponse);
            alertRepository.save(alertToAdd);
        }
    }

    public List<Alert> selection(){
        List<Alert> boslu = new ArrayList<>();
        Alert bos = new Alert();
        bos.setName("");
        boslu.add(bos);
        List<Alert> hepsi = alertRepository.findAll();
        for(Alert bir: hepsi) {
            boslu.add(bir);
        }
        return boslu;
    }

    public Alert takingGraph(String name){
        return  alertRepository.findByName(name);
    }
}
