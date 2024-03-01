package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.Medication;

@Service
public interface MedicationService {

    /**
     * Save medications to database by converting RegisterMedicationDTO to Medication
     *
     * @param medications List of medications needs to be saved to db
     * */
    List<Medication> registerMedications(List<Medication> medications);

    /**
     * Calculate weight of medication that will be saved
     *
     * @param medications List of medications to calculate their weight
     * @return Total weight of medications*/
    double calculateMedicationsWeight(List<Medication> medications);

    /**
     * Utility method to find medications loaded in specific drone
     *
     * @param drone the drone to get medications for it
     * @return List of medications for that drone
     */
    List<MedicationResDTO> findMedications(Drone drone);

    /**
     * Throw exception if medication already exists in db
     *
     * @param medications List of medications to check
     */
    void throwExceptionIfMedicationExists(List<Medication> medications);

}
