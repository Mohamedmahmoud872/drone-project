package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.Medication;

@Service
public interface MedicationService {

    void registerMedications(List<Medication> medications);

    double calculateMedicationsWeight(List<Medication> medications);

    List<MedicationResDTO> findMedications(Drone drone);

    void throwExceptionIfMedicationExists(List<Medication> medications);

}
