package com.bankApp.loansMicroservice.services.impl;

import com.bankApp.loansMicroservice.constants.LoansConstants;
import com.bankApp.loansMicroservice.dtos.LoanDTO;
import com.bankApp.loansMicroservice.entity.Loan;
import com.bankApp.loansMicroservice.exception.LoanAlreadyExistsException;
import com.bankApp.loansMicroservice.exception.LoanNotFoundException;
import com.bankApp.loansMicroservice.mappers.LoanMappers;
import com.bankApp.loansMicroservice.repositories.LoanRepo;
import com.bankApp.loansMicroservice.services.LoanServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanServices {

    private LoanRepo loanRepo;

    /**
     *
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepo.findByMobileNumber(mobileNumber);
        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already exists for mobile number: " + mobileNumber);
        }

        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);

        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        loanRepo.save(newLoan);
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public LoanDTO getLoan(String mobileNumber) {
        Loan loan = loanRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new LoanNotFoundException("Loan", "MobileNumber", mobileNumber)
        );
        return LoanMappers.mapToLoanDTO(loan, new LoanDTO());
    }

    /**
     *
     * @param loanDTO
     * @return
     */
    @Override
    public boolean updateLoan(LoanDTO loanDTO) {
        Loan loan = loanRepo.findByLoanNumber(loanDTO.getLoanNumber()).orElseThrow(
                () -> new LoanNotFoundException("Loan", "LoanNumber", loanDTO.getLoanNumber()));
        LoanMappers.mapToLoan(loanDTO, loan);
        loanRepo.save(loan);
        return  true;
    }

    /**
     * delete Loan
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new LoanNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loanRepo.deleteById(loan.getLoanId());
        return true;
    }
}
