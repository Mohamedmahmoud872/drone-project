package com.example.dronetaskv1.simulation;

import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneState;

public class Simulation {

    public static void simulateDroneActivity(Drone drone, DroneState droneState) {
        drone.setDroneState(droneState);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - generateRandom());
    }

    private static int generateRandom() {
        int min = 1;
        int max = 5;
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

}
