package com.example.dronetaskv1.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.exceptionHandler.DroneNotLoadingException;
import com.example.dronetaskv1.exceptionHandler.DroneWeightExeededException;
import com.example.dronetaskv1.mapper.MedicationMapper;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneState;
import com.example.dronetaskv1.model.Medication;
import com.example.dronetaskv1.service.DroneHelperService;
import com.example.dronetaskv1.service.DroneMedicationService;
import com.example.dronetaskv1.service.MedicationService;

@Service
public class DroneMedicationServiceImpl implements DroneMedicationService {

    private final DroneHelperService droneHelperService;
    private final MedicationMapper medicationMapper;
    private final MedicationService medicationService;

    public DroneMedicationServiceImpl(MedicationService medicationService,
                                      DroneHelperService droneHelperService, MedicationMapper medicationMapper) {
        this.medicationService = medicationService;
        this.droneHelperService = droneHelperService;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public void loadDroneWithMedications(LoadDroneDTO loadDroneDTO) {

        List<Medication> medications = medicationMapper.dtosToMedications(loadDroneDTO.getMedications());
        Drone drone = droneHelperService.findBySerialNumberAndDroneState(loadDroneDTO.getSerialNumber(), DroneState.LOADING);
        medicationService.throwExceptionIfMedicationExists(medications);
        double medicationsWeight = medicationService.calculateMedicationsWeight(medications);
        if (!droneHelperService.isDroneHasSpace(drone, medicationsWeight)) {
            throw new DroneWeightExeededException(Message.DRONE_WEIGHT_EXCEEDED);
        } else {
            loadDrone(medications, medicationsWeight, drone);
        }
    }

    private void loadDrone(List<Medication> medications, double medicationsWeight, Drone drone) {
        medicationService.registerMedications(medications);
        assignDroneToMedications(medications, drone);
        drone.setMedications(medications);
        drone.setLoadedWeight(medicationsWeight + drone.getLoadedWeight());
        changeStateIfDroneIsFull(drone);
        droneHelperService.updateDrone(drone);
    }

    private void changeStateIfDroneIsFull(Drone drone) {
        if (Double.compare(drone.getDroneWeight(), drone.getLoadedWeight()) == 0) {
            drone.setDroneState(DroneState.LOADED);
        }
    }

    private void assignDroneToMedications(List<Medication> medications, Drone drone) {
        for (Medication medication : medications) {
            medication.setDrone(drone);
        }
    }

    @Override
    public List<MedicationResDTO> getMedications(String serialNumber) {
        Drone drone = droneHelperService.getDroneBySerial(serialNumber);
        return medicationService.findMedications(drone);
    }


}
