package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.MedicationResDTO;

@Service
public interface DroneMedicationService {

    List<MedicationResDTO> getMedications(String serialNumber);

    void loadDroneWithMedications(LoadDroneDTO loadDroneDTO);

}
