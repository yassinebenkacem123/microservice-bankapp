package com.bankApp.accountsMicroservice.services;

import com.bankApp.accountsMicroservice.dtos.CustomerDetailsDTO;

public interface CustomerService {

    CustomerDetailsDTO getCustomerDetails(String mobileNumber, String correlationId);

}
