package com.example.dronetaskv1.service;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneModel;
import com.example.dronetaskv1.model.DroneState;
import com.example.dronetaskv1.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DroneHelperServiceTest {

    @Autowired
    DroneHelperService droneHelperService;

    public DroneHelperServiceTest() {

    }

    @Test
    void testGetDroneBYSerial() {
        List<Medication> medications = new ArrayList<>();
        Drone expectedDrone = new Drone("111112", DroneModel.LIGHTWEIGHT, 150, 20, DroneState.IDLE, 0.0, medications);
        Drone actualDrone = droneHelperService.getDroneBySerial("111112");
        assertEquals(expectedDrone, actualDrone);
    }

    @Test
    void testGetDroneBYInvalidSerial() {
        try {
            Drone drone = droneHelperService.getDroneBySerial("12567");
        } catch (Exception e) {
            assertEquals("Didn't find drone with this serial number", e.getMessage());
        }
    }

    @Test
    void testGetDroneBySerialByNull() {
        try {
            Drone drone = droneHelperService.getDroneBySerial(null);
        } catch (Exception e) {
            assertEquals("The given id must not be null", e.getMessage());
        }
    }

    @Test
    void testUpdateDrone() {
        String oldSerial = "111115";
        String newSerial = "123456";
        Drone drone = droneHelperService.getDroneBySerial(oldSerial);
        drone.setSerialNumber(newSerial);
        droneHelperService.updateDrone(drone);
        Drone updatedDrone = droneHelperService.getDroneBySerial(newSerial);
        assertEquals(drone.toString(), updatedDrone.toString());
    }

    @Test
    void testUpdateDroneByNull() {
        try {
            droneHelperService.updateDrone(null);
        } catch (Exception e) {
            assertEquals("Entity must not be null", e.getMessage());
        }
    }

    @Test
    void testIsDroneHasSpace() {
        Drone drone = new Drone("111112", DroneModel.LIGHTWEIGHT, 150, 20, DroneState.IDLE, 0.0, null);
        assertEquals(true, droneHelperService.isDroneHasSpace(drone, 150));
    }

    @Test
    void testIsDroneHasSpaceByNull() {
        try {
            assertEquals(true, droneHelperService.isDroneHasSpace(null, 150));
        } catch (Exception e) {
            assertEquals(Message.DRONE_NOT_EXISTS, e.getMessage());
        }
    }

    @Test
    void testThrowExceptionIfDroneExists() {
        String serial = "111112";
        try {
            droneHelperService.throwExceptionIfDroneExists(serial);
        } catch (Exception e) {
            assertEquals(Message.DRONE_EXISTS, e.getMessage());
        }
    }
}
