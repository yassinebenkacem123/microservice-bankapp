package com.bankApp.loansMicroservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LoanDTO {

    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^|[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile number of the customer",
            example = "9876543210"
    )
    private String mobileNumber;

    @NotEmpty
    @Pattern(regexp = "(^|[0-9]{12})$", message = "Loan number must be 12 digits")
    @Schema(
            description = "Loan Number of the customer", example = "123456789012"
    )
    private String loanNumber;


    @NotBlank(message = "LoanType can not be a null or empty")
    @Schema(
            description = "Type of loan taken by the customer", example = "Home Loan"
    )
    private String loanType;


    @Positive(message = "Total loan amount must be a positive number")
    @Schema(
            description = "Total loan amount taken by the customer", example = "500000"
    )
    private int totalLoan;


    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid", example = "250000"
    )
    private int amountPaid;

    private int outstandingAmount;




    
}
