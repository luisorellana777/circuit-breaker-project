package com.server.circuitbreaker.circuitbreaker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Person {
    private Integer id;
    private String name;
    private Integer age;
}
