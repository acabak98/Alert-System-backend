package com.example.back.backend.repository;

import com.example.back.backend.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Alert findByName(String name);
}
