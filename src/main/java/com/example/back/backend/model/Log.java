package com.example.back.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Log {

    private String nameOfUrl;

    private String adress;

    private Long rate= Long.valueOf(2000);

}
