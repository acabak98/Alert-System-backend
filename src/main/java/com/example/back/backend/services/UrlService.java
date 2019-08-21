package com.example.back.backend.services;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Log;
import com.example.back.backend.model.Url;
import com.example.back.backend.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url laylay(final Url url){
        return urlRepository.save(url);
    }

    public void alerting(final String givenName, Alert yeniAlert){
        Url urlToAdd = urlRepository.findByName(givenName);
        if (urlToAdd != null) {
            urlToAdd.getAlert().add(yeniAlert);
            urlRepository.save(urlToAdd);
        }
    }
}
