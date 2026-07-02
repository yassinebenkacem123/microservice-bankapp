package com.bankApp.accountsMicroservice.controllers;

import com.bankApp.accountsMicroservice.constants.AccountConstats;
import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.exceptions.ResourceNotFoundException;
import com.bankApp.accountsMicroservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService accountService;

    private CustomerDTO customerDTO;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(1234567890L);
        accountDTO.setAccountType(AccountConstats.SAVINGS);
        accountDTO.setBranchAddress(AccountConstats.ADDRESS);

        customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0612345678");
        customerDTO.setAccountDTO(accountDTO);
    }

    @Test
    @DisplayName("POST /api/createAccount should create account and return 201")
    void shouldCreateAccountAndReturn201() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(AccountConstats.STATUS_201)))
                .andExpect(jsonPath("$.statusMsg", is(AccountConstats.MESSAGE_201)));

        Mockito.verify(accountService).createAccount(Mockito.any(CustomerDTO.class));
    }

    @Test
    @DisplayName("GET /api/getAccount should return account details and 200")
    void shouldReturnAccountDetailsAnd200() throws Exception {
        Mockito.when(accountService.getAccount("0612345678")).thenReturn(customerDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0612345678")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Yassine")))
                .andExpect(jsonPath("$.email", is("yassine@gmail.com")))
                .andExpect(jsonPath("$.mobileNumber", is("0612345678")))
                .andExpect(jsonPath("$.accountDTO.accountNumber", is(1234567890)));
    }

    @Test
    @DisplayName("GET /api/getAccount should return 404 when account not found")
    void shouldReturn404WhenAccountNotFound() throws Exception {
        Mockito.when(accountService.getAccount("0612345678"))
                .thenThrow(new ResourceNotFoundException("Account", "mobileNumber", "0612345678"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/getAccount")
                        .param("mobileNumber", "0612345678")
                ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/updateAccount should update account and return 200")
    void shouldUpdateAccountAndReturn200() throws Exception {
        Mockito.when(accountService.updateAccount(Mockito.any(CustomerDTO.class))).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/updateAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(AccountConstats.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(AccountConstats.MESSAGE_200)));
    }

    @Test
    @DisplayName("PUT /api/updateAccount should return 500 when update fails")
    void shouldReturn500WhenUpdateFails() throws Exception {
        Mockito.when(accountService.updateAccount(Mockito.any(CustomerDTO.class))).thenReturn(false);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/updateAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", is(AccountConstats.STATUS_500)));
    }

    @Test
    @DisplayName("DELETE /api/deleteAccount should delete account and return 200")
    void shouldDeleteAccountAndReturn200() throws Exception {
        Mockito.when(accountService.deleteAccount("0612345678")).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/deleteAccount")
                        .param("mobileNumber", "0612345678")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(AccountConstats.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(AccountConstats.MESSAGE_200)));
    }

    @Test
    @DisplayName("POST /api/createAccount should return 400 when validation fails")
    void shouldReturn400WhenValidationFails() throws Exception {
        customerDTO.setName(""); // Invalid name

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/createAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO))
                ).andExpect(status().isBadRequest());
    }
}