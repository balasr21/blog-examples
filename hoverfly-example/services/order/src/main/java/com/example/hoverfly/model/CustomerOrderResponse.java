package com.example.hoverfly.model;


import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerOrderResponse {

    private UUID customerId;

    private String postCode;

    private List<OrderItem> items;

    private Status status;

    private DriverDetails driverDetails;


    public enum Status {
        ORDER_PLACED,
        SHIPMENT_IN_PROGRESS,
        DELIVERED,
        CANCELLED
    }

}
