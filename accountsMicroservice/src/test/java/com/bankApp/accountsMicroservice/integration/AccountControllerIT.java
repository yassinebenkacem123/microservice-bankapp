package com.bankApp.accountsMicroservice.integration;


import com.bankApp.accountsMicroservice.constants.AccountConstats;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.repository.AccountRepo;
import com.bankApp.accountsMicroservice.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;


    @BeforeEach
    void cleanDataBase(){
        accountRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    @DisplayName("Create Account: Should create an Account Successfully")
    void shouldCreateAccountSuccessfully() throws Exception{
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");


        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AccountConstats.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AccountConstats.MESSAGE_201));
    }

    @Test
    @DisplayName("Get Account: Should return Account details successfully")
    void shouldGetAccountSuccessfully() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isCreated());

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0610304455")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Yassine")))
                .andExpect(jsonPath("$.mobileNumber", is("0610304455")))
                .andExpect(jsonPath("$.accountDTO").exists());
    }

    @Test
    @DisplayName("Get Account: Should return 404 when account not found")
    void shouldReturn404WhenAccountNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0000000000")
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update Account: Should update account successfully")
    void shouldUpdateAccountSuccessfully() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isCreated());

        String responseBody = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0610304455")
        ).andReturn().getResponse().getContentAsString();

        CustomerDTO savedCustomer = objectMapper.readValue(responseBody, CustomerDTO.class);
        savedCustomer.setName("Yassine Updated");

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/updateAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedCustomer))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountConstats.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountConstats.MESSAGE_200));
    }

    @Test
    @DisplayName("Delete Account: Should delete account successfully")
    void shouldDeleteAccountSuccessfully() throws Exception {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isCreated());

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/deleteAccount")
                        .param("mobileNumber", "0610304455")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountConstats.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountConstats.MESSAGE_200));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0610304455")
        ).andExpect(status().isNotFound());
    }
}
