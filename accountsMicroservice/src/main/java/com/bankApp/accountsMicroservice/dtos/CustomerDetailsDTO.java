package com.bankApp.accountsMicroservice.dtos;

import com.bankApp.accountsMicroservice.dtos.clientsDTOs.CardDTO;
import com.bankApp.accountsMicroservice.dtos.clientsDTOs.LoanDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Data
@Schema(
        name = "CustomerDetailsDTO",
        description = "customer account, loan, card details"
)
public class CustomerDetailsDTO {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountDTO account;

    @Schema(
            description = "Customer loans details"
    )
    private LoanDTO loan;


    @Schema(
            description = "Customer cards details"
    )
    private CardDTO card;


}
