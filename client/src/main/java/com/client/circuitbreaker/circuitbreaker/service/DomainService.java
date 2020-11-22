package com.client.circuitbreaker.circuitbreaker.service;

import com.client.circuitbreaker.circuitbreaker.domain.Person;
import com.client.circuitbreaker.circuitbreaker.domain.Sale;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class DomainService {

    private final RestTemplate restTemplate;

    private final List<Sale> saleList = Arrays.asList(
            Sale.builder().id(1).amount(1000L).client(Person.builder().id(1).build()).build(),
            Sale.builder().id(2).amount(2000L).client(Person.builder().id(2).build()).build());

    public List<Sale> getSales() {

        for (Sale sale : saleList) {
            Integer idClient = sale.getClient().getId();

            ResponseEntity<Person> entity = restTemplate.exchange("http://localhost:8091/persons/" + idClient, HttpMethod.GET, HttpEntity.EMPTY, Person.class);
            Person person = entity.getBody();
            sale.setClient(person);
        }


        return saleList;
    }
}
