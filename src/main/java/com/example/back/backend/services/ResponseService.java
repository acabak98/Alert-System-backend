package com.example.back.backend.services;

import com.example.back.backend.model.Response;
import com.example.back.backend.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;

    public Response check(Response response) {
        return responseRepository.save(response);
    }

    public List<Response> grafik(){
        return responseRepository.findAll();
    }
}
