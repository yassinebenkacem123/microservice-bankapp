package com.bankApp.accountsMicroservice.services.impl;

import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDetailsDTO;
import com.bankApp.accountsMicroservice.dtos.clientsDTOs.CardDTO;
import com.bankApp.accountsMicroservice.dtos.clientsDTOs.LoanDTO;
import com.bankApp.accountsMicroservice.entity.Account;
import com.bankApp.accountsMicroservice.entity.Customer;
import com.bankApp.accountsMicroservice.exceptions.ResourceNotFoundException;
import com.bankApp.accountsMicroservice.mapper.AccountMapper;
import com.bankApp.accountsMicroservice.mapper.CustomerMapper;
import com.bankApp.accountsMicroservice.repository.AccountRepo;
import com.bankApp.accountsMicroservice.repository.CustomerRepo;
import com.bankApp.accountsMicroservice.services.CustomerService;
import com.bankApp.accountsMicroservice.services.client.CardsFeignClient;
import com.bankApp.accountsMicroservice.services.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private AccountRepo accountRepo;
    private CustomerRepo customerRepo;

    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDTO getCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());

        customerDetailsDTO.setAccount(AccountMapper.mapToAccountDTO(account, new AccountDTO()));

        ResponseEntity<LoanDTO> loanResponse = loansFeignClient.getLoanDetails(correlationId, mobileNumber);
        if(loanResponse != null){
            customerDetailsDTO.setLoan(loanResponse.getBody());
        }
        ResponseEntity<CardDTO> cardResponse = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(cardResponse != null){
            customerDetailsDTO.setCard(cardResponse.getBody());
        }
        return customerDetailsDTO;
    }
}
