package com.example.dronetaskv1.dto;

import java.util.List;

import com.example.dronetaskv1.constant.Constraints;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadDroneDTO {

    @Size(min = Constraints.SERIAL_NUMBER_MIN, max = Constraints.SERIAL_NUMBER_MAX, message = "Drone Serial Number must be in range " + Constraints.SERIAL_NUMBER_MIN + " and " + Constraints.SERIAL_NUMBER_MAX)
    @NotNull(message = "Serial Number must be inserted")
    @NotBlank(message = "Serial Number must not be blank")
    private String serialNumber;

    @NotEmpty(message = "Insert at least one medication")
    private List<RegisterMedicationsDTO> medications;


}
