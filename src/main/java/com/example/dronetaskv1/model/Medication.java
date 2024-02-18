package com.example.dronetaskv1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Entity
@Table(name = "medication")
@Data
public class Medication {

    @NotNull(message = "Medicatin name must be inserted")
    @NotBlank(message = "Medicatin name can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "allowed only letters, numbers, '-', '_' in medication name")
    @Column(name = "medication_name")
    private String medicationName;

    @NotNull(message = "Medicatin weight must be inserted")
    @Column(name = "medication_weight")
    private double medicationWeight;

    @Id
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "allowed only upper case letters, underscore and numbers in medication code")
    @Column(name = "medication_code")
    private String medicationCode;

    @Column(name = "medication_image")
    private String medicationImage;

    @Column(name = "number_of_packages")
    private Integer numberOfPackages = 1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_serial")
    private Drone drone;

    public Medication() {
    }

    public Medication(String medicationName, double medicationWeight, String medicationCode, String medicationImage, Drone drone) {
        this.medicationName = medicationName;
        this.medicationWeight = medicationWeight;
        this.medicationCode = medicationCode;
        this.medicationImage = medicationImage;
        this.drone = drone;
    }

}
