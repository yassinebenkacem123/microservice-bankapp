package com.bankApp.accountsMicroservice.integration;

import com.bankApp.accountsMicroservice.constants.AccountConstats;
import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.entity.Customer;
import com.bankApp.accountsMicroservice.exceptions.CustomerAlreadyExistException;
import com.bankApp.accountsMicroservice.exceptions.ResourceNotFoundException;
import com.bankApp.accountsMicroservice.repository.AccountRepo;
import com.bankApp.accountsMicroservice.repository.CustomerRepo;
import com.bankApp.accountsMicroservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class AccountServiceImplIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    @BeforeEach
    void setUp() {
        accountRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    @DisplayName("createAccount: Should create customer and account in DB")
    void createAccount_Success() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");

        // When
        accountService.createAccount(customerDTO);

        // Then
        Optional<Customer> customer = customerRepo.findByMobileNumber("0610304455");
        assertTrue(customer.isPresent());
        assertEquals("Yassine", customer.get().getName());

        assertTrue(accountRepo.findByCustomerId(customer.get().getCustomerId()).isPresent());
    }

    @Test
    @DisplayName("createAccount: Should throw exception if customer already exists")
    void createAccount_AlreadyExists() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");

        accountService.createAccount(customerDTO);

        // When & Then
        assertThrows(CustomerAlreadyExistException.class, () -> {
            accountService.createAccount(customerDTO);
        });
    }

    @Test
    @DisplayName("getAccount: Should return full customer and account details")
    void getAccount_Success() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");
        accountService.createAccount(customerDTO);

        // When
        CustomerDTO result = accountService.getAccount("0610304455");

        // Then
        assertNotNull(result);
        assertEquals("Yassine", result.getName());
        assertNotNull(result.getAccountDTO());
        assertNotNull(result.getAccountDTO().getAccountNumber());
        assertEquals(AccountConstats.SAVINGS, result.getAccountDTO().getAccountType());
    }

    @Test
    @DisplayName("getAccount: Should throw ResourceNotFoundException when customer not found")
    void getAccount_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccount("0000000000");
        });
    }

    @Test
    @DisplayName("updateAccount: Should update customer details in DB")
    void updateAccount_Success() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");
        accountService.createAccount(customerDTO);

        CustomerDTO savedCustomer = accountService.getAccount("0610304455");
        savedCustomer.setName("Yassine Updated");

        // When
        boolean isUpdated = accountService.updateAccount(savedCustomer);

        // Then
        assertTrue(isUpdated);
        CustomerDTO updatedCustomer = accountService.getAccount("0610304455");
        assertEquals("Yassine Updated", updatedCustomer.getName());
    }

    @Test
    @DisplayName("deleteAccount: Should delete customer and account from DB")
    void deleteAccount_Success() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0610304455");
        accountService.createAccount(customerDTO);

        // When
        boolean isDeleted = accountService.deleteAccount("0610304455");

        // Then
        assertTrue(isDeleted);
        assertFalse(customerRepo.findByMobileNumber("0610304455").isPresent());
    }
}
