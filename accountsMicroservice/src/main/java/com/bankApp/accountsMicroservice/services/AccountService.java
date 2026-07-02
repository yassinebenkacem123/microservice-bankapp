package com.bankApp.accountsMicroservice.services;

import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.entity.Customer;
import org.springframework.stereotype.Service;

public interface AccountService {

    /**
     *
     * @param customerDTO
     */
    void createAccount(CustomerDTO customerDTO);

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDTO getAccount(String mobileNumber);


    /**
     *
     * @param customerDTO
     * @return
     */
    boolean updateAccount(CustomerDTO customerDTO);


    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteAccount(String mobileNumber);

}
