package com.example.hoverfly.model;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOrderRequest {

    private UUID customerId;

    private String postCode;

    private List<OrderItem> items;

}
