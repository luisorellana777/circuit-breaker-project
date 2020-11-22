package com.server.circuitbreaker.circuitbreaker.controller;

import com.server.circuitbreaker.circuitbreaker.domain.Person;
import com.server.circuitbreaker.circuitbreaker.service.DomainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DomainController {

    private DomainService domainService;

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {

        return ResponseEntity.ok(domainService.getPerson(id));
    }
}
