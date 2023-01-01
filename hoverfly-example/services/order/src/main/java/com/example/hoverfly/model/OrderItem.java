package com.example.hoverfly.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private UUID productId;

    private Integer orderQuantity;

    private String instructions;

}
