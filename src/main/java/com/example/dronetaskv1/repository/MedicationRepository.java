package com.example.dronetaskv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findByDrone(Drone drone);

}
