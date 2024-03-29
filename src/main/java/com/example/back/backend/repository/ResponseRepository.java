package com.example.back.backend.repository;

import com.example.back.backend.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Response findByName(String name);
    List<Response> findByNameOrderByIdAsc(String name);
}
