package com.example.back.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer success;

    private Long timeDifference;

//    private LocalDateTime requestTime;
}