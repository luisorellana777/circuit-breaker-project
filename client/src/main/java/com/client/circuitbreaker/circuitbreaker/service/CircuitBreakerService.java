package com.client.circuitbreaker.circuitbreaker.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class CircuitBreakerService {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public <T> T executeSupplier(Supplier<T> supplier, Consumer<Throwable> onFailureConsumer) {
        this.circuitBreakerRegistry.circuitBreaker("default");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.find("default")
                .orElseThrow(() -> new RuntimeException("CircuitBreaker Exception")
                );

        Supplier<T> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier);
        Try<T> result = Try.ofSupplier(decoratedSupplier).onFailure(onFailureConsumer);
        return result.get();
    }
}
