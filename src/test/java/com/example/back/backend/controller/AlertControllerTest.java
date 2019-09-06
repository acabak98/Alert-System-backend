package com.example.back.backend.controller;

import com.example.back.backend.model.Alert;
import com.example.back.backend.services.AlertService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertController.class)
class AlertControllerTest {

    @MockBean
    AlertService alertService;

    @Autowired
    MockMvc mockMvc;

    String name;
    String url;
    Integer period;

    @BeforeEach
    void setUp() {
        name = "aName";
        url = "aUrl";
        period = 3;
    }

    @AfterEach
    void tearDown() {
        reset(alertService);
    }

    @Test
    void addAlert() throws Exception {

        mockMvc.perform(post("/addalert?name=" + name + "&url=" + url + "&controlPeriod=" + period.toString()))
                .andExpect(status().isOk());

        verify(alertService).addAlert(name, url, period);
    }

    @Test
    void selection() throws Exception {
        Alert alert = new Alert();
        //alert.setResponse(new HashSet<>());
        alert.setName("aName");
        alert.setUrl("https://www.turkiye.gov.tr");
        alert.setPeriod(3);
        alert.setRemaining(0);
        alert.setTime(1l);
        alert.setId(1l);
        List<Alert> alertList = new ArrayList<>();
        alertList.add(alert);
        given(alertService.selection()).willReturn(alertList);

        mockMvc.perform(get("/selection"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alert.getName())))
                .andExpect(jsonPath("$[0].url", is(alert.getUrl())))
                .andExpect(jsonPath("$[0].time", is(alert.getTime().intValue())))
                .andExpect(jsonPath("$[0].period", is(alert.getPeriod())))
                .andExpect(jsonPath("$[0].remaining", is(alert.getRemaining())))
                .andExpect(jsonPath("$[0].response", is(alert.getResponse())))
                .andExpect(jsonPath("$[0].id", is(alert.getId().intValue())));

        verify(alertService).selection();
    }
}