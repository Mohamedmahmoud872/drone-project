package com.example.dronetaskv1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicationResDTO {

    private String medicationName;

    private double medicationWeight;

    private String medicationCode;

    private String medicationImage;

    private int numberOfPackages;

}
