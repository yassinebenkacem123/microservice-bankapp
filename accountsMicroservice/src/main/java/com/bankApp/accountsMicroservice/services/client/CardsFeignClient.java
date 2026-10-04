package com.bankApp.accountsMicroservice.services.client;


import com.bankApp.accountsMicroservice.dtos.clientsDTOs.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    // getting the cards .
    @GetMapping("/api/getCard")
    public ResponseEntity<CardDTO> fetchCardDetails(
            @RequestHeader("bank-app-correlation-id") String correlationId,
            @RequestParam  String mobileNumber
    );


}
