package com.example.hoverfly.driver.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDetailsResponse {

    private UUID driverId;

    private String driverName;

    private double rating;

}
