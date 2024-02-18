package com.example.dronetaskv1.seviceImpl;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.dronetaskv1.constant.Constraints;
import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.DroneBatteryDTO;
import com.example.dronetaskv1.dto.DroneResponseDTO;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.exceptionHandler.DroneNotFoundException;
import com.example.dronetaskv1.history.History;
import com.example.dronetaskv1.mapper.DroneMapper;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.DroneModel;
import com.example.dronetaskv1.model.DroneState;
import com.example.dronetaskv1.repository.DroneRepository;
import com.example.dronetaskv1.service.DroneHelperService;
import com.example.dronetaskv1.service.DroneService;
import com.example.dronetaskv1.simulation.Simulation;

import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService, DroneHelperService {

    private final DroneRepository droneRepository;
    private final  DroneMapper droneMapper;
    private History history;
    

    @Override
    public DroneResponseDTO addDrone(RegisterDroneDTO drone) {
        checkIfDroneExists(drone.getSerialNumber());
        Drone myDrone = droneMapper.registDroneDtotoDrone(drone);

        myDrone.setDroneState(DroneState.classifyState(drone.getBatteryCapacity()));
        myDrone.setDroneModel(DroneModel.classifyModel(drone.getDroneWeight()));
        myDrone.setLoadedWeight(0.0);

        Drone savedDrone = updateDrone(myDrone);
        return droneMapper.registerDroneDTO(savedDrone);
    }

    @Override
    public List<DroneResponseDTO> getAvailableDrones() {
        List<Drone> drones = findByDroneState(DroneState.LOADING);
        return droneMapper.availableDronestoDTO(drones);
    }

    @Override
    public DroneBatteryDTO getDroneBattery(String serialNumber) {

        Drone theDrone = getDroneBySerial(serialNumber);

        return droneMapper.droneBatterytoDTO(theDrone);
    }


    @Scheduled(fixedRate = 4000)
    private void deleverMedication() {
        List<Drone> loadedDrones = findByDroneState(DroneState.LOADED);
        for (Drone drone : loadedDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERING);
            history.updateHistory(drone, Message.DRONE_DELIVERING);
        }
        updateDrones(loadedDrones);
    }

    @Scheduled(fixedRate = 6000)
    private void unloadDrone() {
        List<Drone> deliveringDrones = findByDroneState(DroneState.DELIVERING);
        for (Drone drone : deliveringDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERED);
            drone.setLoadedWeight(0.0);
            history.updateHistory(drone, Message.DRONE_DELEVERED);
        }
        updateDrones(deliveringDrones);
    }

    @Scheduled(fixedRate = 8000)
    private void returnDrone() {
        List<Drone> returningDrones = findByDroneState(DroneState.DELIVERED);
        for (Drone drone : returningDrones) {
            Simulation.simulateDroneActivity(drone, DroneState.RETURNING);
            history.updateHistory(drone, Message.DRONE_RETURNING);
        }
        updateDrones(returningDrones);
    }

    @Scheduled(fixedRate = 6000)
    private void checkDrone() {
        List<Drone> returningDrones = findByDroneState(DroneState.RETURNING);
        for (Drone drone : returningDrones) {
            if(drone.getBatteryCapacity() <= Constraints.BATTERY_CAPACITY_LIMIT) {
                Simulation.simulateDroneActivity(drone, DroneState.IDLE);
            }else {
                Simulation.simulateDroneActivity(drone, DroneState.LOADING);
            }
            history.updateHistory(drone, Message.DRONE_RETURNED);
        }
        updateDrones(returningDrones);
    }

    @Override
    public Drone getDroneBySerial(String serial) {
        return droneRepository.findById(serial)
                .orElseThrow(() -> new DroneNotFoundException(Message.DRONE_NOT_EXISTS));
    }

    @Override
    public Drone updateDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public void checkIfDroneExists(String serial) {
        if (droneRepository.existsById(serial)) {
            throw new DroneNotFoundException(Message.DRONE_EXISTS);
        }
    }

    @Override
    public boolean isDroneHasSpace(Drone drone, double medicationsWeight) {
        double availableWeight = drone.getDroneWeight() - drone.getLoadedWeight();
        return !(availableWeight < medicationsWeight);
    }

    @Override
    public List<Drone> findByDroneState(DroneState droneState) {
        return droneRepository.findByDroneState(droneState);
    }

    @Override
    public void updateDrones(List<Drone> drones) {
        droneRepository.saveAll(drones);
    }

}
