package com.example.hoverfly.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDetails {

    @JsonProperty("driverId")
    private UUID driverId;

    @JsonProperty("driverName")
    private String driverName;

    @JsonProperty("rating")
    private double rating;

}
