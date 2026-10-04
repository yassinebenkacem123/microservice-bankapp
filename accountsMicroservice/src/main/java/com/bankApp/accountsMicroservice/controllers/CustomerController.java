package com.bankApp.accountsMicroservice.controllers;

import com.bankApp.accountsMicroservice.dtos.CustomerDetailsDTO;
import com.bankApp.accountsMicroservice.dtos.ErrorResponseDTO;
import com.bankApp.accountsMicroservice.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for customer in The Bank App",
        description = "CRUD REST APIs - Create Customer, Update Customer, Get Customer, Get All Customers, Delete Customer"
)
@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;

    }

    @Operation(
            summary = "Get customer details by mobile number",
            description = "Provide a mobile number to look up specific customer details"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved customer details"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internel Server Error",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDTO.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(
            @RequestHeader("bank-app-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
            String mobileNumber
    ){

        logger.debug("FetchCustomerDetails method Start");
        CustomerDetailsDTO customerDetails = customerService.getCustomerDetails(mobileNumber, correlationId);
        logger.debug("FetchCustomerDetails method End");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
    }


}
