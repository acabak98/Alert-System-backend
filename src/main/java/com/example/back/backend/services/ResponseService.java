package com.example.back.backend.services;
import com.example.back.backend.model.Response;
import com.example.back.backend.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;

    public List<Long> graphOfTime(String name){
        List<Long> listOfResponseTime = new ArrayList<Long>();
        List<Response> listOfResponse =  responseRepository.findByNameOrderByIdAsc(name);
        for(Response singleResponse : listOfResponse) {
            listOfResponseTime.add(singleResponse.getTimeDifference());
        }
        return listOfResponseTime;
    }

    public List<Integer> graphOfSuccess(String name) {
        List<Integer> listOfSuccess = new ArrayList<Integer>();
        List<Response> listOfResponse = responseRepository.findByNameOrderByIdAsc(name);
        for (Response singleResponse : listOfResponse) {
            listOfSuccess.add(singleResponse.getSuccess());
        }
        return listOfSuccess;
    }
}
