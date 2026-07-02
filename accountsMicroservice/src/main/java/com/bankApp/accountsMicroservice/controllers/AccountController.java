package com.bankApp.accountsMicroservice.controllers;


import com.bankApp.accountsMicroservice.constants.AccountConstats;
import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.AccountsContactInfoDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.dtos.ResponseDTO;
import com.bankApp.accountsMicroservice.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "CRUD REST APIs For accounts in Bank Application",
        description = "CRUD REST APIs that would allow the user to create, get, delete, update a Bank Account."
)
@RestController
@RequestMapping("/api")
@Validated
public class AccountController {

    private final AccountService accountService;

    public  AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @Autowired
    private AccountsContactInfoDTO accountsContactInfoDTO;


    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody @Valid CustomerDTO customerDTO) {
        accountService.createAccount(customerDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstats.STATUS_201, AccountConstats.MESSAGE_201));
    }

    @GetMapping("/getAccount")
    public ResponseEntity<CustomerDTO> getAccount(
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber
    ) {
        CustomerDTO customerDTO = accountService.getAccount(mobileNumber);
        return ResponseEntity.ok(customerDTO);
    }

    @PutMapping("/updateAccount")
    public  ResponseEntity<ResponseDTO> updateAccount(@RequestBody @Valid CustomerDTO customerDTO) {
        boolean isUpdated = accountService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity.ok(new ResponseDTO(AccountConstats.STATUS_200, AccountConstats.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(AccountConstats.STATUS_500, AccountConstats.MESSAGE_500));
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<ResponseDTO> deleteAccount(
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber
    ) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDTO(AccountConstats.STATUS_200, AccountConstats.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(AccountConstats.STATUS_500, AccountConstats.MESSAGE_500));
        }
    }

    @GetMapping("/account-info")
    public ResponseEntity<AccountsContactInfoDTO> getAccountInfo(){
        return  ResponseEntity.ok()
                .body(accountsContactInfoDTO);
    }
}
