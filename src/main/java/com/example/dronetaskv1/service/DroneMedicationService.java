package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.MedicationResDTO;

@Service
public interface DroneMedicationService {

    /**
     * Get medications loaded in a specific drone
     *
     * @param serialNumber Serial number of the drone
     * @return List of medications loaded in that drone
     */
    List<MedicationResDTO> getMedications(String serialNumber);

    /**
     * Load medications to drone
     *
     * @param loadDroneDTO DTO object containing serial number of the drone and medications
     */
    void loadDroneWithMedications(LoadDroneDTO loadDroneDTO);

}
