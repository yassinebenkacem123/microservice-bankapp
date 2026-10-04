package com.bankApp.loansMicroservice.mappers;

import com.bankApp.loansMicroservice.dtos.LoanDTO;
import com.bankApp.loansMicroservice.entity.Loan;

public class LoanMappers {

    public static LoanDTO mapToLoanDTO(Loan loan, LoanDTO loanDTO){

        loanDTO.setLoanNumber(loan.getLoanNumber());
        loanDTO.setLoanType(loan.getLoanType());
        loanDTO.setTotalLoan(loan.getTotalLoan());
        loanDTO.setAmountPaid(loan.getAmountPaid());
        loanDTO.setMobileNumber(loan.getMobileNumber());
        loanDTO.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDTO;
    }

    public static Loan mapToLoan(LoanDTO loanDTO, Loan loan){
        loan.setLoanNumber(loanDTO.getLoanNumber());
        loan.setLoanType(loanDTO.getLoanType());
        loan.setTotalLoan(loanDTO.getTotalLoan());
        loan.setAmountPaid(loanDTO.getAmountPaid());
        loan.setMobileNumber(loanDTO.getMobileNumber());
        loan.setOutstandingAmount(loanDTO.getOutstandingAmount());
        return loan;
    }
}
