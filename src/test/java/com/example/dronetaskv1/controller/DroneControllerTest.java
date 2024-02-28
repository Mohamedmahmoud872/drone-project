package com.example.dronetaskv1.controller;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.RegisterDroneDTO;
import com.example.dronetaskv1.model.DroneModel;
import com.example.dronetaskv1.model.DroneState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {

    @Autowired
    MockMvc mockMvc;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void testRegisterDrone() throws Exception {
        RegisterDroneDTO registerDroneDTO = new RegisterDroneDTO("111116", 350, 90);
        String postValue = OBJECT_MAPPER.writeValueAsString(registerDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/drone/register-drone")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(postValue))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serialNumber").value("111116"))
                .andExpect(jsonPath("$.droneModel").value(String.valueOf(DroneModel.CRUISEWEIGHT)))
                .andExpect(jsonPath("$.batteryCapacity").value(90))
                .andExpect(jsonPath("$.droneState").value(String.valueOf(DroneState.LOADING)))
                .andExpect(jsonPath("$.loadedWeight").value(0.0));
    }

    @Test
    void testRegisterDroneByInvalidData() throws Exception {
        RegisterDroneDTO registerDroneDTO = new RegisterDroneDTO("11111", 350, 90);
        String postValue = OBJECT_MAPPER.writeValueAsString(registerDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/drone/register-drone")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(postValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAvailableDrones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/drone/available-drones"))
                .andExpect(status().isOk());
    }

    @Test
    void testDroneBattery() throws Exception {
        String serial = "111113";
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/drone/drone-battery/{serial}", serial)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("111113"))
                .andExpect(jsonPath("$.batteryCapacity").value(60));
    }

    @Test
    void testDroneBatteryByInvalidSerial() throws Exception {
        String serial = "1111134";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/drone/drone-battery/{serial}", serial)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value(Message.DRONE_NOT_EXISTS));
    }
}
