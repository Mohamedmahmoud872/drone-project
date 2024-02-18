package com.example.dronetaskv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;

@Service
public interface DroneService {
    
    DroneResponseDTO addDrone(RegisterDroneDTO drone);
    
    List<DroneResponseDTO> getAvailableDrones();

    DroneBatteryDTO getDroneBattery(String serialNumber);

}
