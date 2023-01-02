package com.example.hoverfly.controller;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.hoverfly.model.CustomerOrderRequest;
import com.example.hoverfly.model.CustomerOrderResponse;
import com.example.hoverfly.model.OrderItem;

import io.specto.hoverfly.junit.core.config.LogLevel;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflySimulate;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@HoverflySimulate(
        config = @HoverflyConfig(
                proxyPort = 8501, // Proxy port for hoverfly
                adminPort = 8889, // Admin port for accessing hoverfly dashboard
                plainHttpTunneling = true, // To skip TLS handshake for the purpose of demo
                logLevel = LogLevel.INFO),
        source = @HoverflySimulate.Source(value = "hoverfly-proxy-server-driver-service-simulation.json", type = HoverflySimulate.SourceType.CLASSPATH))
@ExtendWith(HoverflyExtension.class)
@ActiveProfiles("proxy-server")
class ProxyServerOrderControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testOrderService_with_hoverfly_as_proxy_server() {
        CustomerOrderResponse response = webTestClient
                                                      .post()
                                                      .uri("/order")
                                                      .body(BodyInserters
                                                                    .fromPublisher(Mono.just(CustomerOrderRequest
                                                                                                     .builder()
                                                                                                     .customerId(UUID.randomUUID())
                                                                                                     .items(List.of(OrderItem
                                                                                                                            .builder()
                                                                                                                            .orderQuantity(2)
                                                                                                                            .productId(UUID.randomUUID())
                                                                                                                            .build()))
                                                                                                     .postCode("SL6")
                                                                                                     .build()), CustomerOrderRequest.class))
                                                      .exchange().expectStatus()
                                                      .isEqualTo(HttpStatus.CREATED)
                                                      .expectBody(CustomerOrderResponse.class).returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getDriverDetails());
        Assertions.assertEquals(UUID.fromString("01bec8a3-4119-43cb-9a81-f1131dcbb632"), response.getDriverDetails().getDriverId());
    }

}