package com.example.back.backend.services;

import com.example.back.backend.model.Alert;
import com.example.back.backend.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;

    public Alert check(Alert alert) {
        return alertRepository.save(alert);
    }
}
