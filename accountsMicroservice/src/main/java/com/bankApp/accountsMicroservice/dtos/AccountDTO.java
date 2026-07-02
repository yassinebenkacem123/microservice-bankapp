package com.bankApp.accountsMicroservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountDTO {

    @Pattern(regexp = "^[0-9]{10}$", message = "Account number must be 10 digits")
    @NotEmpty(message = "Account number cannot be empty")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @NotBlank(message = "Branch address cannot be blank")
    private String branchAddress;

}
