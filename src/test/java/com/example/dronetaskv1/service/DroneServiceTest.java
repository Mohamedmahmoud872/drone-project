package com.example.dronetaskv1.service;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.model.DroneState;
import com.example.dronetaskv1.model.DroneModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DroneServiceTest {

    @Autowired
    private DroneService droneService;

    @Test
    void testRegisterDrone() {
        RegisterDroneDTO registerDroneDTO = new RegisterDroneDTO("111117", 340, 60);
        DroneResponseDTO expectedDroneResponseDTO = new DroneResponseDTO("111117", DroneModel.CRUISEWEIGHT, 60, DroneState.LOADING, 0.0);
        DroneResponseDTO droneResponseDTO = droneService.addDrone(registerDroneDTO);
        assertEquals(expectedDroneResponseDTO, droneResponseDTO);
    }

    @Test
    void testRegisterDroneByNull() {
        try {
            DroneResponseDTO droneResponseDTO = droneService.addDrone(null);
        }catch (Exception e) {
            assertEquals(Message.EMPTY_DATA, e.getMessage());
        }
    }

    @Test
    void testRegisterDroneByEmpty() {
        RegisterDroneDTO registerDroneDTO = new RegisterDroneDTO();
        try {
            DroneResponseDTO droneResponseDTO = droneService.addDrone(registerDroneDTO);
        }catch (Exception e) {
            assertEquals(Message.EMPTY_DATA, e.getMessage());
        }
    }

    @Test
    void testGetAvailableDrones() {
        List<DroneResponseDTO> expectedDroneResponseDTO = new ArrayList<>();
        DroneResponseDTO droneResponseDTO1 = new DroneResponseDTO("111113", DroneModel.MIDDLEWEIGHT, 60, DroneState.LOADING, 50);
        DroneResponseDTO droneResponseDTO2 = new DroneResponseDTO("111114", DroneModel.HEAVYWEIGHT, 80, DroneState.LOADING, 150);
        DroneResponseDTO droneResponseDTO3 = new DroneResponseDTO("111115", DroneModel.LIGHTWEIGHT, 50, DroneState.LOADING, 0);
        DroneResponseDTO droneResponseDTO4 = new DroneResponseDTO("111117", DroneModel.CRUISEWEIGHT, 60, DroneState.LOADING, 0);
        expectedDroneResponseDTO.add(droneResponseDTO1);
        expectedDroneResponseDTO.add(droneResponseDTO2);
        expectedDroneResponseDTO.add(droneResponseDTO3);
        expectedDroneResponseDTO.add(droneResponseDTO4);
        List<DroneResponseDTO> droneResponseDTOS = droneService.getAvailableDrones();

        assertEquals(expectedDroneResponseDTO, droneResponseDTOS);
    }

    @Test
    void testGetDroneBattery() {
        DroneBatteryDTO expectedDroneBatteryDTO = new DroneBatteryDTO("111115", 50);
        DroneBatteryDTO droneBatteryDTO = droneService.getDroneBattery("111115");

        assertEquals(expectedDroneBatteryDTO, droneBatteryDTO);
    }

    @Test
    void testGetDroneBatteryInvalidSerial() {
        try {
            DroneBatteryDTO droneBatteryDTO = droneService.getDroneBattery("123456");
        }catch (Exception e) {
            assertEquals(Message.DRONE_NOT_EXISTS, e.getMessage());
        }
    }

    @Test
    void testGetDroneBatteryByNull() {
        try {
            DroneBatteryDTO droneBatteryDTO = droneService.getDroneBattery(null);
        }catch (Exception e) {
            assertEquals("The given id must not be null", e.getMessage());
        }
    }




}
