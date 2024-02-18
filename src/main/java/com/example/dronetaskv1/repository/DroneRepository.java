package com.example.dronetaskv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneState;

import java.util.List;


public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> findByDroneState(DroneState droneState);

    Drone findBySerialNumberAndDroneState(String serialNumber, DroneState droneState);

}
