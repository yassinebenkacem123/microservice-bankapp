package com.bankApp.accountsMicroservice.services.client;

import com.bankApp.accountsMicroservice.dtos.clientsDTOs.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDTO> getLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
