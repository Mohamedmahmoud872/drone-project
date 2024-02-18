package com.example.dronetaskv1.dto;

import com.example.dronetaskv1.model.DroneModel;
import com.example.dronetaskv1.model.DroneState;

import lombok.Data;

@Data
public class DroneResponseDTO {

    private String serialNumber;

    private DroneModel droneModel;

    private int batteryCapacity;

    private DroneState droneState;

    private double loadedWeight;

}
