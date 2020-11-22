package com.client.circuitbreaker.circuitbreaker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Sale {
    private Integer id;
    private Person client;
    private Long amount;
}
