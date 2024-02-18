package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.exceptionHandler.DroneNotFoundException;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneState;

@Service
public interface DroneHelperService {

    Drone updateDrone(Drone drone);

    void updateDrones(List<Drone> drones);

    Drone getDroneBySerial(String serial) throws DroneNotFoundException;

    boolean isDroneHasSpace(Drone drone, double medicationsWeight);

    void checkIfDroneExists(String serial) throws DroneNotFoundException;

    List<Drone> findByDroneState(DroneState droneState);

}
