package com.bankApp.accountsMicroservice.mapper;

import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToCustomerDTO(CustomerDTO customerDTO, Customer customer){
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        return customerDTO;
    }

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer){
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }
}
