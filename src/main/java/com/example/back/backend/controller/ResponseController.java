package com.example.back.backend.controller;
import com.example.back.backend.services.ResponseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ResponseController {
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    private final ResponseService responseService;

    @GetMapping("/timegraph")
    public List<Long> graphOfTime(@RequestParam String name){
        return responseService.graphOfTime(name);
    }

    @GetMapping("/successgraph")
    public List<Integer> graphOfSuccess(@RequestParam String name) {
        return responseService.graphOfSuccess(name);
    }
}
