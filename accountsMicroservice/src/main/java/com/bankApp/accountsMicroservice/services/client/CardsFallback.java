package com.bankApp.accountsMicroservice.services.client;

import com.bankApp.accountsMicroservice.dtos.clientsDTOs.CardDTO;
import jdk.jfr.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardDTO> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
