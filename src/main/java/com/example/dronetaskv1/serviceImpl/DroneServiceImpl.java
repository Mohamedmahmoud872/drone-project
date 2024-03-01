package com.example.dronetaskv1.serviceImpl;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.dronetaskv1.constant.Constraints;
import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.exceptionHandler.DroneNotFoundException;
import com.example.dronetaskv1.history.History;
import com.example.dronetaskv1.mapper.DroneMapper;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneModel;
import com.example.dronetaskv1.model.DroneState;
import com.example.dronetaskv1.repository.DroneRepository;
import com.example.dronetaskv1.service.DroneHelperService;
import com.example.dronetaskv1.service.DroneService;
import com.example.dronetaskv1.simulation.Simulation;

import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService, DroneHelperService {

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;
    private final History history;

    /**
     * Registers drone to Database after its data being set
     *
     * @param drone required data of drone and rest of data are set by app
     * @return Response data of the drone
     */
    @Override
    public DroneResponseDTO addDrone(RegisterDroneDTO drone) {
        validateDrone(drone);
        Drone myDrone = droneMapper.registDroneDtotoDrone(drone);

        myDrone.setDroneState(DroneState.classifyState(drone.getBatteryCapacity()));
        myDrone.setDroneModel(DroneModel.classifyModel(drone.getDroneWeight()));
        myDrone.setLoadedWeight(0.0);

        Drone savedDrone = updateDrone(myDrone);
        return droneMapper.registerDroneDTO(savedDrone);
    }

    /**
     * Lists all available drones that ready to deliver medications
     *
     * @return All drones are in loading state
     */
    @Override
    public List<DroneResponseDTO> getAvailableDrones() {
        List<Drone> drones = findByDroneState(DroneState.LOADING);
        return droneMapper.availableDronestoDTO(drones);
    }

    /**
     * Check drone battery for a given drone
     *
     * @param serialNumber serial number for the drone
     * @return DTO containing the battery level for the passed serial number
     */
    @Override
    public DroneBatteryDTO getDroneBattery(String serialNumber) {

        Drone theDrone = getDroneBySerial(serialNumber);

        return droneMapper.droneBatterytoDTO(theDrone);
    }

    /**
     * Simulation of delivering the Medications
     */
    @Scheduled(fixedRate = 4000)
    private void deliverMedication() {
        List<Drone> loadedDrones = findByDroneState(DroneState.LOADED);
        for (Drone drone : loadedDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERING);
            history.updateHistory(drone, Message.DRONE_DELIVERING);
        }
        updateDrones(loadedDrones);
    }

    /**
     * Simulation of unloading the Medications
     */
    @Scheduled(fixedRate = 6000)
    private void unloadDrone() {
        List<Drone> deliveringDrones = findByDroneState(DroneState.DELIVERING);
        for (Drone drone : deliveringDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERED);
            drone.setLoadedWeight(0.0);
            history.updateHistory(drone, Message.DRONE_DELIVERED);
        }
        updateDrones(deliveringDrones);
    }

    /**
     * Simulation of returning drone to the Base
     */
    @Scheduled(fixedRate = 8000)
    private void returnDrone() {
        List<Drone> returningDrones = findByDroneState(DroneState.DELIVERED);
        for (Drone drone : returningDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.RETURNING);
            history.updateHistory(drone, Message.DRONE_RETURNING);
        }
        updateDrones(returningDrones);
    }

    /**
     * Simulation of check if drone still has enough battery capacity to be in loading state
     */
    @Scheduled(fixedRate = 6000)
    private void checkDrone() {
        List<Drone> returningDrones = findByDroneState(DroneState.RETURNING);
        for (Drone drone : returningDrones) {
            if (drone.getBatteryCapacity() <= Constraints.BATTERY_CAPACITY_LIMIT) {
                Simulation.simulateDroneActivity(drone, DroneState.IDLE);
            } else {
                Simulation.simulateDroneActivity(drone, DroneState.LOADING);
            }
            history.updateHistory(drone, Message.DRONE_RETURNED);
        }
        updateDrones(returningDrones);
    }

    /**
     * Gets drone by serial number from database
     *
     * @param serial Serial number of drone we searched for
     * @return The drone if exists in database
     */
    @Override
    public Drone getDroneBySerial(String serial) {
        return droneRepository.findById(serial)
                .orElseThrow(() -> new DroneNotFoundException(Message.DRONE_NOT_EXISTS));
    }

    /**
     * Utility function to save or update drone in database
     *
     * @param drone Drone to be updated
     * @return The updated drone
     */
    @Override
    public Drone updateDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    /**
     * Check if drone has enough space to load medications in it
     *
     * @param drone The drone to be checked
     * @param medicationsWeight Weight of medications need to be loaded
     * @return True if drone has space and False otherwise
     */
    @Override
    public boolean isDroneHasSpace(Drone drone, double medicationsWeight) {
        throwExceptionIfDroneNull(drone);
        double availableWeight = drone.getDroneWeight() - drone.getLoadedWeight();
        return !(availableWeight < medicationsWeight);
    }

    /**
     * Gets drones by state from database
     *
     * @param droneState State of drones to search by
     * @return List of drones with the passed state
     */
    @Override
    public List<Drone> findByDroneState(DroneState droneState) {
        return droneRepository.findByDroneState(droneState);
    }

    /**
     * Gets drones by state and serial number from database
     *
     * @param serialNumber Serial number of drone we searched for
     * @param droneState State of drone to search by
     * @return Drone with passed serial number and state
     */
    @Override
    public Drone findBySerialNumberAndDroneState(String serialNumber, DroneState droneState) {
        return droneRepository.findBySerialNumberAndDroneState(serialNumber, droneState);
    }

    /**
     * Utility function to save or update list of drones in database
     *
     * @param drones Drones to be updated
     */
    @Override
    public void updateDrones(List<Drone> drones) {
        droneRepository.saveAll(drones);
    }

    /**
     * Throw exception if drone already exists in database
     *
     * @param serial Serial number of drone to check
     */
    @Override
    public void throwExceptionIfDroneExists(String serial) {
        if (droneRepository.existsById(serial)) {
            throw new DroneNotFoundException(Message.DRONE_EXISTS);
        }
    }

    /**
     * Throws Exception if given RegisterDroneDTO object is null or doesn't include serial number
     *
     * @param drone The RegisterDroneDTO object to check
     */
    private void throwExceptionIfDroneNull(RegisterDroneDTO drone) {
        if (drone == null || drone.getSerialNumber() == null) {
            throw new DroneNotFoundException(Message.EMPTY_DATA);
        }

    }

    /**
     * Throws Exception if given RegisterDroneDTO object is null or doesn't include serial number
     *
     * @param drone The RegisterDroneDTO object to check
     */
    private void validateDrone(RegisterDroneDTO drone) {
        throwExceptionIfDroneNull(drone);
        throwExceptionIfDroneExists(drone.getSerialNumber());
    }

    /**
     * Throws Exception if drone is null
     *
     * @param drone The drone to be checked
     */
    private void throwExceptionIfDroneNull(Drone drone) {
        if(drone == null) throw new DroneNotFoundException(Message.DRONE_NOT_EXISTS);
    }

}
