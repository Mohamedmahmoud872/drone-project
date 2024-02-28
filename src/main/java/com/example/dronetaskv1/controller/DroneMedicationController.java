package com.example.dronetaskv1.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.service.DroneMedicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/meds")
public class DroneMedicationController {

    private DroneMedicationService droneMedicationService;

    public DroneMedicationController(DroneMedicationService droneMedicationService) {
        this.droneMedicationService = droneMedicationService;
    }


    @PostMapping("/load-medications")
    public ResponseEntity<?> loadMedicationsToDrone(@RequestBody @Valid LoadDroneDTO loadDroneDTO) {

        droneMedicationService.loadDroneWithMedications(loadDroneDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(loadDroneDTO);

    }

    @GetMapping("/get-medications/{serial}")
    public ResponseEntity<List<MedicationResDTO>> getMedications(@PathVariable String serial) {

        List<MedicationResDTO> medications = droneMedicationService.getMedications(serial);
        return ResponseEntity.status(HttpStatus.OK).body(medications);

    }

}
