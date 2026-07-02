package com.bankApp.loansMicroservice.services;

import com.bankApp.loansMicroservice.dtos.LoanDTO;

public interface LoanServices {
    /**
     * Creates a new loan for the given mobile number
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);


    LoanDTO getLoan(String mobileNumber);


    boolean updateLoan(LoanDTO loanDTO);

    boolean deleteLoan(String mobileNumber);




}
