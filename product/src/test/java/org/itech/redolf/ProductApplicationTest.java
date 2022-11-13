package org.itech.redolf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.itech.redolf.dto.ProductRequest;
import org.itech.redolf.dto.ProductResponse;
import org.itech.redolf.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductApplicationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductRepository repository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }
    public ProductRequest data(){
        return ProductRequest.builder()
                .name("Alien ware 20")
                .description("512 SSD, 1 TB")
                .price(BigDecimal.valueOf(160000))
                .build();
    }
    @Test
    void createProduct() throws Exception {
        ProductRequest productRequest = data();
        String value=mapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value))
                .andExpect(status().isCreated());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getAllProducts() throws Exception {
          List<ProductResponse> response = products();
          String data = mapper.writeValueAsString(response);
           mockMvc.perform(MockMvcRequestBuilders.get("/api/products/findAll")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(data)).andExpect(status().isOk());
    }

    private List<ProductResponse> products(){
        ProductResponse one_ = ProductResponse.builder()
                .id(UUID.randomUUID().toString())
                .name("Iphone 13")
                .description("New release")
                .price(BigDecimal.valueOf(12000))
                .build();
        ProductResponse two_ = ProductResponse.builder()
                .id(UUID.randomUUID().toString())
                .name("Hp omen 20")
                .description("New release 512 SSD, 1TB, 16GB RAM")
                .price(BigDecimal.valueOf(15000))
                .build();
        return List.of(one_, two_);
    }

    @Test
    void deleteProductByName() throws Exception {
        ProductRequest productRequest = data();
        String value=mapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/delete/Alien ware 20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value))
                .andExpect(status().isOk());

    }
}