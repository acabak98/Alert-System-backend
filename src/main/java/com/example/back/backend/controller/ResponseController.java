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

    @GetMapping("/aa")
    public List<Integer> grafik(@RequestParam String name){
        List<Integer> dataResponse = new ArrayList<Integer>();
        List<Response> alldata = responseService.grafik();
        List<Response> dataByName = new ArrayList<>();
        for (Response alldatum : alldata) {
            if (alldatum.getName() == name){
                dataByName.add(alldatum);
            }
        }
        for(Response data: dataByName) {
            dataResponse.add(data.getSuccess());
        }
        return dataResponse;
    }
}
