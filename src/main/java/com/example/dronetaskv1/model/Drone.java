package com.example.dronetaskv1.model;

import java.util.List;

import com.example.dronetaskv1.constant.Constraints;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drone")
@Data
@NoArgsConstructor
public class Drone {

    @Id
    @Size(min = Constraints.SERIAL_NUMBER_MIN, max = Constraints.SERIAL_NUMBER_MAX, message = "Drone Serial Number must be in range " + Constraints.SERIAL_NUMBER_MIN + " and " + Constraints.SERIAL_NUMBER_MAX)
    @NotNull(message = "Serial Number must be inserted")
    @NotBlank(message = "Serial Number can not be blank")
    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model")
    private DroneModel droneModel;

    @NotNull(message = "Drone Weight must be inserted")
    @DecimalMax(value = "500", message = "Maximum weight that drone can carry is: " + Constraints.DRONE_WEIGHT_MAX + " gr")
    @DecimalMin(value = "100", message = "Minimum weight that drone can carry is: " + Constraints.DRONE_WEIGHT_MIN + " gr")
    @Column(name = "drone_weight")
    private double droneWeight;

    @NotNull(message = "Battery Capacity must be inserted")
    @Min(value = Constraints.BATTERY_CAPACITY_MIN, message = "Battery capacity can not be under " + Constraints.BATTERY_CAPACITY_MIN)
    @Max(value = Constraints.BATTERY_CAPACITY_MAX, message = "Battery capacity can not be over " + Constraints.BATTERY_CAPACITY_MAX)
    @Column(name = "battery_capacity")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state")
    private DroneState droneState;

    @Column(name = "loaded_weight")
    private double loadedWeight = 0;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    private List<Medication> medications;


    public Drone(String serialNumber, DroneModel droneModel, double droneWeight, int batteryCapacity, DroneState droneState, double loadedWeight, List<Medication> medications) {
        this.serialNumber = serialNumber;
        this.droneModel = droneModel;
        this.droneWeight = droneWeight;
        this.batteryCapacity = batteryCapacity;
        this.droneState = droneState;
        this.loadedWeight = loadedWeight;
        this.medications = medications;
    }

}
