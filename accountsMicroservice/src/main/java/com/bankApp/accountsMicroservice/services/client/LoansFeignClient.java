package com.bankApp.accountsMicroservice.services.client;

import com.bankApp.accountsMicroservice.dtos.clientsDTOs.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    // getting the loans .
    @GetMapping("/api/getLoan")
    public ResponseEntity<LoanDTO> getLoanDetails(
            @RequestHeader("bank-app-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}
