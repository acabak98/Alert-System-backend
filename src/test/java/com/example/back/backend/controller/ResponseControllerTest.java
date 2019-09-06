package com.example.back.backend.controller;

import com.example.back.backend.services.ResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ResponseControllerTest {

    @Mock
    ResponseService responseService;

    @InjectMocks
    ResponseController controller;

    MockMvc mockMvc;

    String name;

    @BeforeEach
    void setUp() {
        name = "aName";
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void graphOfTime() throws Exception {
        List<Long> longList = new  ArrayList<>();
        longList.add(50L);
        given(responseService.graphOfTime(name)).willReturn(longList);

        mockMvc.perform(get("/timegraph?name=" + name))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0]", is(longList.get(0).intValue())));

        verify(responseService).graphOfTime(name);
    }

    @Test
    void graphOfSuccess() throws Exception {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(20);
        given(responseService.graphOfSuccess(name)).willReturn(integerList);

        mockMvc.perform(get("/successgraph?name=" + name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is(integerList.get(0))));

        verify(responseService).graphOfSuccess(name);
    }
}