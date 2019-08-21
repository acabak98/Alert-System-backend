package com.example.back.backend.repository;

import com.example.back.backend.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByName(String name);
}
