package com.example.dronetaskv1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DroneBatteryDTO {

    private String serialNumber;

    private int batteryCapacity;

}
