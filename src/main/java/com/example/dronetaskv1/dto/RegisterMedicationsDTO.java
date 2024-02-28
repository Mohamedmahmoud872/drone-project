package com.example.dronetaskv1.dto;


import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMedicationsDTO {

    @NotNull(message = "Medicatin name must be inserted")
    @NotBlank(message = "Medicatin name can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "allowed only letters, numbers, '-', '_' in medication name")
    private String medicationName;

    @DecimalMin(value = "1", message = "Medication weight can not be less than 1 gr")
    @DecimalMax(value = "500", message = "Medication weight can not be more than 500 gr")
    @NotNull(message = "Medicatin weight must be inserted")
    private double medicationWeight;

    @Id
    @NotNull(message = "Medicatin code must be inserted")
    @NotBlank(message = "Medicatin code can not be blank")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "allowed only upper case letters, underscore and numbers in medication code")
    private String medicationCode;

    private String medicationImage;

    private int numberOfPackages = 1;

}
