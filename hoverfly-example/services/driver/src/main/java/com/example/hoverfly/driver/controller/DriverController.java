package com.example.hoverfly.driver.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoverfly.driver.model.DriverDetailsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class DriverController {

    @GetMapping(value = {"driver/{postCode}"},
            produces = {"application/json;charset=UTF-8"})
    public Mono<ResponseEntity<DriverDetailsResponse>> getDriverDetails(@PathVariable("postCode") String postCode) {
        if (postCode.equals("SW1W0NY")) {
            return Mono.just(ResponseEntity.ok(DriverDetailsResponse
                                                       .builder()
                                                       .driverId(UUID.randomUUID())
                                                       .driverName("Chandler Bing")
                                                       .rating(4.9d)
                                                       .build()));
        } else if (postCode.equals("PO167GZ")) {
            return Mono.just(ResponseEntity.ok(DriverDetailsResponse
                                                       .builder()
                                                       .driverId(UUID.randomUUID())
                                                       .driverName("Jack Bauer")
                                                       .rating(4.7d)
                                                       .build()));
        } else if (postCode.equals("GU167HF")) {
            return Mono.just(ResponseEntity.ok(DriverDetailsResponse
                                                       .builder()
                                                       .driverId(UUID.randomUUID())
                                                       .driverName("Walter White")
                                                       .rating(5.0d)
                                                       .build()));
        } else {
            return Mono.just(ResponseEntity.ok(DriverDetailsResponse
                                                              .builder()
                                                              .driverId(UUID.randomUUID())
                                                              .driverName("John Doe")
                                                              .rating(4.6d)
                                                              .build()));
        }
    }

}
