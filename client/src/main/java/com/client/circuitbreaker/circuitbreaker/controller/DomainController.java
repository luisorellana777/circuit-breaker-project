package com.client.circuitbreaker.circuitbreaker.controller;

import com.client.circuitbreaker.circuitbreaker.domain.Sale;
import com.client.circuitbreaker.circuitbreaker.service.CircuitBreakerService;
import com.client.circuitbreaker.circuitbreaker.service.DomainService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
public class DomainController {

    private DomainService domainService;
    private CircuitBreakerService circuitBreakerService;

    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getSales() {

        List<Sale> saleList = circuitBreakerService
                .executeSupplier(
                        () -> domainService.getSales(),
                        this::circuitBreakerOnFailure);

        return ResponseEntity.ok(saleList);
    }

    private void circuitBreakerOnFailure(Throwable throwable) {
        log.error("****** This is a failure attempt ******");
        throw new RuntimeException(throwable.getMessage());
    }
}
