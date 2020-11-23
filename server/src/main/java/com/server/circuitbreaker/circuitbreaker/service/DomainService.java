package com.server.circuitbreaker.circuitbreaker.service;

import com.server.circuitbreaker.circuitbreaker.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DomainService {

    @Value("${server.sleep}")
    private Integer sleepThread;

    List<Person> personList = Arrays.asList(
            Person.builder().id(1).name("Luis Orellana").age(32).build(),
            Person.builder().id(2).name("Carlos Qultik").age(30).build());

    public Person getPerson(int id) {

        try {
            Thread.sleep(sleepThread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return personList.stream().filter(person -> person.getId().equals(id)).findFirst().orElse(Person.builder().build());
    }
}
