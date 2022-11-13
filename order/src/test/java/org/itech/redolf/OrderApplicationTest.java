package org.itech.redolf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itech.redolf.dto.OrderLineItemsRequest;
import org.itech.redolf.dto.OrderLineItemsResponse;
import org.itech.redolf.dto.OrderRequest;
import org.itech.redolf.dto.OrderResponse;
import org.itech.redolf.repository.OrderRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private  ObjectMapper  objectMapper;
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.database",mySQLContainer::getDatabaseName);
        dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
    }

    @Test
    void placeOrder() throws Exception {
        OrderRequest request = request();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1,repository.findAll().size());
    }

    @Test
    void fetchAllOrders() throws Exception {
        OrderResponse response = response();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/list/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    private OrderResponse response(){
        OrderLineItemsResponse items = OrderLineItemsResponse
                .builder()
                .quantity(20)
                .skuCode(UUID.randomUUID().toString())
                .price(BigDecimal.valueOf(12000))
                .build();

        return OrderResponse
                .builder()
                .orderNumber(UUID.randomUUID().toString())
                .id(1)
                .orderLineItems(Stream.of(items).toList())
                .build();
    }
    private OrderRequest request(){
        OrderLineItemsRequest items = OrderLineItemsRequest.builder()
                .skuCode(UUID.randomUUID().toString())
                .quantity(4)
                .price(BigDecimal.valueOf(1200))
                .build();
        return OrderRequest
                .builder()
                .orderLineItemsRequests(List.of(items))
                .build();
    }
}