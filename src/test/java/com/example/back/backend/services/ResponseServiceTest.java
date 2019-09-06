package com.example.back.backend.services;

import com.example.back.backend.model.Response;
import com.example.back.backend.repository.ResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseServiceTest {

    @Mock
    ResponseRepository responseRepository;

    @InjectMocks
    ResponseService service;

    Response response;

    @BeforeEach
    void setUp() {
        response = new Response();
    }

    @Test
    void graphOfTime() {
        response.setTimeDifference((long) 1);

        List<Response> responseList = new ArrayList<Response>();
        responseList.add(response);

        when(responseRepository.findByNameOrderByIdAsc(anyString())).thenReturn(responseList);

        List<Long> found = service.graphOfTime(anyString());

        verify(responseRepository).findByNameOrderByIdAsc(anyString());

        assertThat(found).hasSize(1);

        assertEquals(response.getTimeDifference(), found.get(0));

    }

    @Test
    void graphOfSuccess() {
        response.setSuccess(1);

        List<Response> responseList = new ArrayList<Response>();
        responseList.add(response);

        when(responseRepository.findByNameOrderByIdAsc(anyString())).thenReturn(responseList);

        List<Integer> found = service.graphOfSuccess(anyString());

        verify(responseRepository).findByNameOrderByIdAsc(anyString());

        assertThat(found).hasSize(1);
        assertEquals(response.getSuccess(), found.get(0));
    }
}