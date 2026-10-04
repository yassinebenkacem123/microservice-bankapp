package com.bankApp.accountsMicroservice.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountDTO accountDTO;
}
