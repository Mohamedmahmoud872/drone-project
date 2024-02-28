package com.example.dronetaskv1.controller;

import com.example.dronetaskv1.dto.LoadDroneDTO;
import com.example.dronetaskv1.dto.RegisterMedicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DroneMedicationControllerTest {

    @Autowired
    MockMvc mockMvc;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testLoadMedecationsToDrone() throws Exception {
        String serial = "111113";
        List<RegisterMedicationsDTO> medications = new ArrayList<>();
        RegisterMedicationsDTO med1 = new RegisterMedicationsDTO("VitaminC", 50.0, "VIT1234", "image.png", 1);
        RegisterMedicationsDTO med2 = new RegisterMedicationsDTO("Omega3", 100.0, "OMEGA3", "image.png", 1);
        medications.add(med1);
        medications.add(med2);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        String postValue = OBJECT_MAPPER.writeValueAsString(loadDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/meds/load-medications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(postValue))
                .andExpect(status().isCreated());
    }

    @Test
    public void testLoadMedecationsToDroneByInvalidData() throws Exception {
        String serial = "111113";
        List<RegisterMedicationsDTO> medications = new ArrayList<>();
        RegisterMedicationsDTO med1 = new RegisterMedicationsDTO("", 50.0, "VIT1234", "image.png", 1);
        medications.add(med1);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serial, medications);
        String postValue = OBJECT_MAPPER.writeValueAsString(loadDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/meds/load-medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetMedications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/meds/get-medications/{serial}", "111113"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicationsByInvalidSerial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/meds/get-medications/{serial}", "1111134"))
                .andExpect(status().isNotFound());
    }
}
