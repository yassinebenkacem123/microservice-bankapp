package com.bankApp.accountsMicroservice.mapper;

import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void mapToCustomerDTOTest() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setMobileNumber("1234567890");

        CustomerDTO customerDTO = new CustomerDTO();
        CustomerMapper.mapToCustomerDTO(customerDTO, customer);

        assertEquals(customer.getName(), customerDTO.getName());
        assertEquals(customer.getEmail(), customerDTO.getEmail());
        assertEquals(customer.getMobileNumber(), customerDTO.getMobileNumber());
    }

    @Test
    void mapToCustomerTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Jane Doe");
        customerDTO.setEmail("jane@example.com");
        customerDTO.setMobileNumber("0987654321");

        Customer customer = new Customer();
        CustomerMapper.mapToCustomer(customerDTO, customer);

        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getEmail(), customer.getEmail());
        assertEquals(customerDTO.getMobileNumber(), customer.getMobileNumber());
    }
}