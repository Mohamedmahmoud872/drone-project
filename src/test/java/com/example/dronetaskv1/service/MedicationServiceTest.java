package com.example.dronetaskv1.service;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.mapper.MedicationMapper;
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
class MedicationServiceTest {

    @Autowired
    private MedicationService medicationService;
    @Autowired
    private MedicationMapper medicationMapper;
    private final List<Medication> medicationList;

    public MedicationServiceTest() {
        medicationList = new ArrayList<>();

        Medication med1 = new Medication("VitaminC", 50, "VIT1234", "image.png", null);
        Medication med2 = new Medication("Omega3", 100, "OMEGA3", "image.png", null);
        medicationList.add(med2);
        medicationList.add(med1);
    }

    @Test
    void testRegisterMedications() {
        assertEquals(medicationList, medicationService.registerMedications(medicationList));
    }

    @Test
    void testCalculateMedicationsWeight() {
        double expectedWeight = 150.0;
        double actualWeight = medicationService.calculateMedicationsWeight(medicationList);
        assertEquals(expectedWeight, actualWeight);
    }

    @Test
    void testCalculateMedicationsWeightWithNull() {
        double actualWeight = medicationService.calculateMedicationsWeight(null);
        assertEquals(0.0, actualWeight);
    }

    @Test
    void testCalculateMedicationsWeightWithEmpty() {
        List<Medication> emptyList = new ArrayList<>();
        double actualWeight = medicationService.calculateMedicationsWeight(emptyList);
        assertEquals(0.0, actualWeight);
    }

    @Test
    void testExceptionIfMedicationExists() {
        List<Medication> actualList = new ArrayList<>();
        actualList.add(new Medication("Omega3", 100, "OMEGA3", "image.png", null));
        try {
            medicationService.throwExceptionIfMedicationExists(actualList);
        } catch (Exception e) {
            assertEquals(Message.MEDICATION_EXISTS, e.getMessage());
        }
    }

}
