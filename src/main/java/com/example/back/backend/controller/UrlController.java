package com.example.back.backend.controller;

import com.example.back.backend.model.Url;
import com.example.back.backend.services.UrlService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/loyloy")
    public Url laylay(@RequestParam String name, @RequestParam String url){
        Url urlToPush = new Url();
        urlToPush.setName(name);
        urlToPush.setUrl(url);
        return urlService.laylay(urlToPush);
    }
}