package com.example.back.backend.services;

import com.example.back.backend.model.Alert;
import com.example.back.backend.model.Response;
import com.example.back.backend.repository.AlertRepository;
import com.example.back.backend.repository.ResponseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    AlertRepository alertRepository;

    @Spy
    @InjectMocks
    AlertService service;

    @Test
    void addAlert() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setUrl("https://turkiye.gov.tr");
        List<Alert> alertList = new ArrayList<>();
        alertList.add(alert);

        service.addAlert("someName", alert.getUrl(), 3);

        verify(alertRepository,atLeast(1)).save(any(Alert.class));
        verify(service).checkRemainingTime();
    }

    @Test
    void checkRemainingTimeOne() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setRemaining(1);
        List<Alert> alertList = new ArrayList<>();
        alertList.add(alert);

        when(alertRepository.findAll()).thenReturn(alertList);

        service.checkRemainingTime();

        verify(alertRepository).findAll();
        verify(alertRepository).save(alert);
        verify(service, never()).connection(alert);

        assertThat(alert.getRemaining()).isEqualTo(0);
    }

    @Test
    void checkRemainingTimeZeroCorrectUrl() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setUrl("https://turkiye.gov.tr");
        alert.setRemaining(0);
        List<Alert> alertList = new ArrayList<>();
        alertList.add(alert);

        when(alertRepository.findAll()).thenReturn(alertList);

        service.checkRemainingTime();

        verify(alertRepository).findAll();
        verify(service).connection(alert);
    }

    @Test
    void checkRemainingTimeZeroWrongUrl() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setName("");
        alert.setUrl("https://turkie.gov.tr");
        alert.setRemaining(0);
        alert.setResponse(new HashSet<>());
        List<Alert> alertList = new ArrayList<>();
        alertList.add(alert);

        when(alertRepository.findAll()).thenReturn(alertList);
        when(alertRepository.findByName(anyString())).thenReturn(alert);

        service.checkRemainingTime();

        verify(alertRepository).findAll();

        verify(alertRepository, times(2)).save(alert);
        verify(alertRepository).findByName(anyString());
        assertThat(alert.getResponse()).hasSize(1);
    }

    @Test
    void connection200() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setName("");
        alert.setResponse(new HashSet<>());
        alert.setUrl("https://turkiye.gov.tr");

        when(alertRepository.findByName(anyString())).thenReturn(alert);

        service.connection(alert);

        verify(alertRepository, times(2)).save(alert);
        verify(alertRepository).findByName(anyString());
        assertThat(alert.getResponse()).hasSize(1);
    }

    @Test
    void connectionCatchTest() {
    }

    @Test
    void connectionNot200() throws MalformedURLException, ProtocolException {
        Alert alert = new Alert();
        alert.setName("");
        alert.setResponse(new HashSet<>());
        alert.setUrl("https://www.tukiye.gov.tr");

        when(alertRepository.findByName(anyString())).thenReturn(alert);

        service.connection(alert);

        verify(alertRepository, times(2)).save(alert);
        verify(alertRepository).findByName(anyString());
        assertThat(alert.getResponse()).hasSize(1);
    }

    @Test
    void addResponseNotNull() {
        Alert alert = new Alert();
        alert.setResponse(new HashSet<>());
        Response response = new Response();

        when(alertRepository.findByName(anyString())).thenReturn(alert);

        service.addResponse("someName", response);

        verify(alertRepository).findByName(anyString());
        verify(alertRepository).save(alert);

        assertThat(alert.getResponse()).hasSize(1);
    }

    @Test
    void addResponseNull() {
        Alert alert = null;
        Response response = new Response();

        when(alertRepository.findByName(anyString())).thenReturn(alert);

        service.addResponse("someName", response);

        verify(alertRepository).findByName(anyString());
        verify(alertRepository, never()).save(alert);
    }

    @Test
    void selection() {
        List<Alert> alertList = new ArrayList<>();
        Alert alert = new Alert();
        alertList.add(alert);

        when(alertRepository.findAll()).thenReturn(alertList);

        List<Alert> returnedList = service.selection();

        verify(alertRepository).findAll();

        assertThat(returnedList).hasSize(2);
        assertEquals("", returnedList.get(0).getName());
    }
}