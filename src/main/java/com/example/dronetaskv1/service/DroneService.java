package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;

@Service
public interface DroneService {

    /**
     * Register new drones to database after its data being set
     *
     * @param drone required data of drone and rest of data are set by app
     * @return response data of the registered drone
     */
    DroneResponseDTO addDrone(RegisterDroneDTO drone);

    /**
     * Lists all available drones that ready to deliver medications
     *
     * @return All drones are in loading state
     */
    List<DroneResponseDTO> getAvailableDrones();

    /**
     * Check drone battery for a given drone
     *
     * @param serialNumber serial number for the drone
     * @return DTO containing the battery level for the passed serial number
     * */
    DroneBatteryDTO getDroneBattery(String serialNumber);

}
