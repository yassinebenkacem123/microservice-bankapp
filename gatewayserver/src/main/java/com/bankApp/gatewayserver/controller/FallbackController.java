package com.bankApp.gatewayserver.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<HashMap<String, String>> contactSupport() {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "The service is currently unavailable. Please contact support.");
        return Mono.just(response);
    }
}
