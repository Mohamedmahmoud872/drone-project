package com.example.dronetaskv1.dto;

import com.example.dronetaskv1.constant.Constraints;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDroneDTO {
    

    @Size(min = Constraints.SERIAL_NUMBER_MIN, max = Constraints.SERIAL_NUMBER_MAX, message = "Drone Serial Number must be in range " + Constraints.SERIAL_NUMBER_MIN + " and " + Constraints.SERIAL_NUMBER_MAX)
    @NotNull(message = "Serial Number must be inserted")
    @NotBlank(message = "Serial Number can not be blank")
    private String serialNumber;

    @DecimalMax(value = "500", message = "Maximum weight that drone can carry is: " + Constraints.DRONE_WEIGHT_MAX + " gr")
    @DecimalMin(value = "100", message = "Minimum weight that drone can carry is: " + Constraints.DRONE_WEIGHT_MIN + " gr")
    @NotNull(message = "Drone Weight must be inserted")
    private double droneWeight;

    @NotNull(message = "Battery Capacity must be inserted")
    @Min(value = Constraints.BATTERY_CAPACITY_MIN, message = "Battery capacity can not be under " + Constraints.BATTERY_CAPACITY_MIN)
    @Max(value = Constraints.BATTERY_CAPACITY_MAX, message = "Battery capacity can not be over " + Constraints.BATTERY_CAPACITY_MAX)
    private int batteryCapacity;

}
