package com.example.back.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    @Id
    @GeneratedValue
    private Long id;

    public int alert;

    private Long timeDifference;

    //private Long currentTime;
}