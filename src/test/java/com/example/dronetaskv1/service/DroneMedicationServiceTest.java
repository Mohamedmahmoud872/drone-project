package com.example.dronetaskv1.service;

import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.dto.RegisterMedicationsDTO;
import com.example.dronetaskv1.mapper.MedicationMapper;
import com.example.dronetaskv1.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DroneMedicationServiceTest {

    @Autowired
    private DroneMedicationService droneMedicationService;
    @Autowired
    private MedicationMapper medicationMapper;
    private List<RegisterMedicationsDTO> medications;
    private List<Medication> actualMeds;

    public DroneMedicationServiceTest() {
        medications = new ArrayList<>();
        actualMeds = new ArrayList<>();
    }

    @Test
    void testLoadDroneWithMedications() {
        String serial = "111113";
        RegisterMedicationsDTO med1 = new RegisterMedicationsDTO("VitaminC", 50.0, "VIT1234", "image.png", 1);
        RegisterMedicationsDTO med2 = new RegisterMedicationsDTO("Omega3", 100.0, "OMEGA3", "image.png", 1);
        medications.add(med1);
        medications.add(med2);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        droneMedicationService.loadDroneWithMedications(loadDroneDTO);
        List<Medication> expectedMeds = medicationMapper.resDtoToMedications(droneMedicationService.getMedications(serial));
        actualMeds = medicationMapper.dtosToMedications(medications);
        assertEquals(expectedMeds, actualMeds);
    }

    @Test
    void testLoadDroneWithMedicationsByInvalidSerial() {
        String serial = "1111132";
        RegisterMedicationsDTO med2 = new RegisterMedicationsDTO("Omega4", 100.0, "OMEGA4", "image.png", 1);
        medications.add(med2);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        try {
            droneMedicationService.loadDroneWithMedications(loadDroneDTO);
        } catch (Exception e) {
            assertEquals("Didn't find drone with this serial number", e.getMessage());
        }
    }

    @Test
    void testLoadDroneWithExistingMedication() {
        String serial = "111113";
        RegisterMedicationsDTO med1 = new RegisterMedicationsDTO("VitaminC", 50.0, "VIT1234", "image.png", 1);
        medications.add(med1);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        try {
            droneMedicationService.loadDroneWithMedications(loadDroneDTO);
        } catch (Exception e) {
            assertEquals("Medication code already exists", e.getMessage());
        }
    }

    @Test
    void testLoadDroneWithOverWeight() {
        String serial = "111115";
        RegisterMedicationsDTO med2 = new RegisterMedicationsDTO("Zinc", 100.0, "ZINC", "image.png", 2);
        medications.add(med2);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        try {
            droneMedicationService.loadDroneWithMedications(loadDroneDTO);
        } catch (Exception e) {
            assertEquals("Medications you tried to load had weight more than available in this drone", e.getMessage());
        }
    }

    @Test
    void testGetMedications() {
        String serial = "111114";
        RegisterMedicationsDTO med1 = new RegisterMedicationsDTO("VitaminD", 50.0, "VIT1235", "image.png", 1);
        medications.add(med1);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        droneMedicationService.loadDroneWithMedications(loadDroneDTO);
        List<MedicationResDTO> meds = droneMedicationService.getMedications(serial);
        assertEquals(medicationMapper.resDtoToMedications(meds), medicationMapper.dtosToMedications(medications));
    }

    @Test
    void testGetMedicationsToEmptyDrone() {
        String serial = "111115";
        try {
            droneMedicationService.getMedications(serial);
        } catch (Exception e) {
            assertEquals("Drone not containing any medications yet", e.getMessage());
        }
    }

    @Test
    void testGetMedicationsToNonExistingDrone() {
        String serial = "1111132";
        try {
            droneMedicationService.getMedications(serial);
        } catch (Exception e) {
            assertEquals("Didn't find drone with this serial number", e.getMessage());
        }
    }

}
