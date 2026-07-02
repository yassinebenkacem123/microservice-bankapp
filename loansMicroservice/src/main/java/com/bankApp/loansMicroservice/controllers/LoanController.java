package com.bankApp.loansMicroservice.controllers;


import com.bankApp.loansMicroservice.constants.LoansConstants;
import com.bankApp.loansMicroservice.dtos.LoanContactInfoDTO;
import com.bankApp.loansMicroservice.dtos.LoanDTO;
import com.bankApp.loansMicroservice.dtos.ResponseDTO;
import com.bankApp.loansMicroservice.services.LoanServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.TableGenerator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for loans in The Bank App",
        description = "CRUD REST APIs - Create Loan, Update Loan, Get Loan, Get All Loans, Delete Loan"

)
@RestController
@RequestMapping("/api")
@Validated
public class LoanController {

    private final  LoanServices loanServices;

    public LoanController(LoanServices loanServices){
        this.loanServices = loanServices;
    }

    @Autowired
    private LoanContactInfoDTO loanContactInfoDTO;

    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDTO> createLoan(
            @RequestParam
            @Pattern(regexp = "^|[0-9]{10}$", message = "Mobile number must be 10 digits")
            String mobileNumber
            ){

        loanServices.createLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        LoansConstants.STATUS_201,
                        LoansConstants.MESSAGE_201
                ));
    }



    @GetMapping("/getLoan")
    public ResponseEntity<?> getLoanDetails(
            @RequestParam
            @Pattern(regexp = "^|[0-9]{10}$", message = "Mobile number must be 10 digits")
            String mobileNumber
    ){
        LoanDTO loanDTO = loanServices.getLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanDTO);
    }


    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDTO> updateLoanDetails(
            @Valid
            @RequestBody LoanDTO loanDTO
    ){
        boolean isUpdated = loanServices.updateLoan(loanDTO);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(
                            LoansConstants.STATUS_200,
                            LoansConstants.MESSAGE_200
                    ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(
                            LoansConstants.STATUS_417,
                            LoansConstants.MESSAGE_417
                    ));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoanDetails(
            @RequestParam
            @Pattern(regexp = "^|[0-9]{10}$", message = "Mobile number must be 10 digits")
            String mobileNumber
    ){

        boolean isDeleted = loanServices.deleteLoan(mobileNumber);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(
                            LoansConstants.STATUS_200,
                            LoansConstants.MESSAGE_200
                    ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(
                            LoansConstants.STATUS_417,
                            LoansConstants.MESSAGE_417
                    ));
        }
    }


    @GetMapping("/loan-info")
    public  ResponseEntity<LoanContactInfoDTO> getLoanInfo(){
        return ResponseEntity.ok()
                .body(loanContactInfoDTO);
    }


}
