package com.example.dronetaskv1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.service.DroneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/drone")
public class DroneController {

    private DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/register-drone")
    public ResponseEntity<DroneResponseDTO> registerDrone(@Valid @RequestBody RegisterDroneDTO drone) {
        DroneResponseDTO registeredDrone = droneService.addDrone(drone);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDrone);
    }

    @GetMapping("/available-drones")
    public ResponseEntity<List<DroneResponseDTO>> getAvailableDrones() {
        return ResponseEntity.status(HttpStatus.OK).body(droneService.getAvailableDrones());
    }

    @GetMapping("/drone-battery/{serial}")
    public ResponseEntity<?> droneBattery(@PathVariable String serial) {
        return ResponseEntity.status(HttpStatus.OK).body(droneService.getDroneBattery(serial));
    }

}
