package org.itech.redolf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itech.redolf.dto.InventoryRequest;
import org.itech.redolf.dto.InventoryResponse;
import org.itech.redolf.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryApplicationTest {

    @Autowired
    private  InventoryRepository inventoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        inventoryRepository.deleteAll();
    }

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.database",mySQLContainer::getDatabaseName);
        dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
    }

    @Test
    void createInventory() throws Exception {
        InventoryRequest request = InventoryRequest
                .builder()
                .skuCode(UUID.randomUUID().toString())
                .quantity(12)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1,inventoryRepository.findAll().size());
    }

    @Test
    void isInStock() throws Exception {
        InventoryResponse response = InventoryResponse
                .builder()
                .skuCode("Gamepad")
                .isInStock(true)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/Gamepad")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(response)))
                .andExpect(status().isOk());

    }

    @Test
    void getAllInventory() throws Exception {
        List<InventoryResponse> response = items();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/findAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    private List<InventoryResponse> items(){
        InventoryResponse response = InventoryResponse
                .builder()
                .skuCode("Gamepad")
                .build();
        return List.of(response,response);
    }
}