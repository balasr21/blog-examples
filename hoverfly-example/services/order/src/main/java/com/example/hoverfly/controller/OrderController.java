package com.example.hoverfly.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.example.hoverfly.model.CustomerOrderRequest;
import com.example.hoverfly.model.CustomerOrderResponse;
import com.example.hoverfly.model.DriverDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    @Value("${driver.service.uri}")
    private String driverServiceEndpoint;
    private final WebClient webClient;

    @PostMapping(
            path = "order",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<CustomerOrderResponse>> createOrder(@RequestBody Mono<CustomerOrderRequest> customerOrderRequestMono, ServerWebExchange serverWebExchange) {
        return customerOrderRequestMono.flatMap(customerOrderRequest ->
                                                        webClient
                                                                .get()
                                                                .uri(URI.create(driverServiceEndpoint + customerOrderRequest.getPostCode()))
                                                                .retrieve().bodyToMono(String.class)
                                                                .flatMap(this::mapDriverResponse)
                                                                .flatMap(driverDetails -> Mono.just(new ResponseEntity<>(CustomerOrderResponse
                                                                                                                                 .builder()
                                                                                                                                 .customerId(customerOrderRequest.getCustomerId())
                                                                                                                                 .postCode(customerOrderRequest.getPostCode())
                                                                                                                                 .status(CustomerOrderResponse.Status.ORDER_PLACED)
                                                                                                                                 .items(customerOrderRequest.getItems())
                                                                                                                                 .driverDetails(driverDetails).build(),
                                                                                                                         HttpStatus.CREATED)))
        );
    }

    private Mono<DriverDetails> mapDriverResponse(String body) {
        try {
            return Mono.just(new ObjectMapper().readValue(body, DriverDetails.class));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

}
