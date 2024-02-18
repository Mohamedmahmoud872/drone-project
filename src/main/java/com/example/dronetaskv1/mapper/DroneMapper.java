package com.example.dronetaskv1.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.model.Drone;

@Mapper(componentModel = "spring")
public interface DroneMapper {

    DroneBatteryDTO droneBatterytoDTO(Drone drone);

    List<DroneResponseDTO> availableDronestoDTO(List<Drone> drones);
    
    DroneResponseDTO registerDroneDTO(Drone drone);

    @InheritInverseConfiguration
    Drone registDroneDtotoDrone(RegisterDroneDTO drone);

}
