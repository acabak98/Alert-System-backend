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

    public void addAlert(String name, String url, Integer controlPeriod) throws MalformedURLException, ProtocolException {
        Alert alertToPost = new Alert();
        alertToPost.setName(name);
        alertToPost.setUrl(url);
        alertToPost.setPeriod(controlPeriod);
        alertToPost.setRemaining(0);
        alertToPost.setTime(System.currentTimeMillis());
        alertRepository.save(alertToPost);
        this.checkRemainingTime();
    }

    @Scheduled(fixedRate = 1000)
    public void checkRemainingTime() throws MalformedURLException, ProtocolException {

        List<Alert> alertList = alertRepository.findAll();

        for (Alert singleAlert : alertList) {
            if (singleAlert.getRemaining()==0) {
                connection(singleAlert);
            }
            else {
                singleAlert.setRemaining(singleAlert.getRemaining()-1);
                alertRepository.save(singleAlert);
            }
        }
    }

    @Async
    public void connection(Alert item) throws ProtocolException, MalformedURLException {
        Response newResponse = new Response();
        newResponse.setName(item.getName());
        item.setTime(System.currentTimeMillis());
        item.setRemaining(item.getPeriod());
        alertRepository.save(item);
        long milliStart = System.currentTimeMillis();
        URL urlToConnect = new URL(item.getUrl());
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) urlToConnect.openConnection();
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
            this.addResponse(item.getName(), newResponse);
        }
        else {
            newResponse.setSuccess(0);
            this.addResponse(item.getName(), newResponse);
        }
    }


    public void addResponse(final String name, Response response){
        Alert alertToAddResponse = alertRepository.findByName(name);
        if (alertToAddResponse != null) {
            alertToAddResponse.getResponse().add(response);
            alertRepository.save(alertToAddResponse);
        }
    }

    public List<Alert> selection(){
        List<Alert> alertListToSelect = new ArrayList<>();
        Alert emptyAlert = new Alert();
        emptyAlert.setName("");
        alertListToSelect.add(emptyAlert);
        List<Alert> alertList = alertRepository.findAll();
        for(Alert singleAlert: alertList) {
            alertListToSelect.add(singleAlert);
        }
        return alertListToSelect;
    }
}
