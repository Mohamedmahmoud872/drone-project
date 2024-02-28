package com.example.dronetaskv1.history;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.serviceImpl.DroneServiceImpl;

@Component
public class LoggerHistory implements History {

    static final Logger logger = Logger.getLogger(DroneServiceImpl.class.getName());

    @Override
    public void updateHistory(Drone drone, String event) {
        logger.info("Drone capacity check at "+ formatEventDate());
        logger.info("drone with serial number " + drone.getSerialNumber() + " has " + drone.getBatteryCapacity() + " battery capacity due to " + event);
    }

}
