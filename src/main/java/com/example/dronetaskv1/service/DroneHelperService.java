package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.exceptionHandler.DroneNotFoundException;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneState;

@Service
public interface DroneHelperService {

    /**
     * Utility function to save or update drone in database
     *
     * @param drone Drone to be updated
     * @return The updated drone
     */
    Drone updateDrone(Drone drone);

    /**
     * Utility function to save or update list of drones in database
     *
     * @param drones Drones to be updated
     */
    void updateDrones(List<Drone> drones);

    /**
     * Gets drone by serial number from database
     *
     * @param serial Serial number of drone we searched for
     * @return The drone if exists in database
     */
    Drone getDroneBySerial(String serial);

    /**
     * Check if drone has enough space to load medications in it
     *
     * @param drone The drone to be checked
     * @param medicationsWeight Weight of medications need to be loaded
     * @return True if drone has space and False otherwise
     */
    boolean isDroneHasSpace(Drone drone, double medicationsWeight);

    /**
     * Throw exception if drone already exists in database
     *
     * @param serial Serial number of drone to check
     */
    void throwExceptionIfDroneExists(String serial);

    /**
     * Gets drones by state from database
     *
     * @param droneState State of drones to search by
     * @return List of drones with the passed state
     */
    List<Drone> findByDroneState(DroneState droneState);

    /**
     * Gets drones by state and serial number from database
     *
     * @param serialNumber Serial number of drone we searched for
     * @param droneState State of drone to search by
     * @return Drone with passed serial number and state
     */
    Drone findBySerialNumberAndDroneState(String serialNumber, DroneState droneState);

}
