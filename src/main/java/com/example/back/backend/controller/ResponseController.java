package com.example.back.backend.controller;

import com.example.back.backend.model.Response;
import com.example.back.backend.services.ResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ResponseController {
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    private final ResponseService responseService;

    @PostMapping("/leylimley")
    public Response check(@RequestBody Response response) {
        return responseService.check(response);
    }

    @GetMapping("/graph")
    public List<Integer> grafik(){
        List<Integer> dataResponse = new ArrayList<Integer>();
        List<Response> alldata = responseService.grafik();
        for (Response alldatum : alldata) {
            dataResponse.add(alldatum.getAlert());
        }
        return dataResponse;
    }
}
