package com.bankApp.loansMicroservice.dtos;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loans")
public record LoanContactInfoDTO(
        String message,
        java.util.Map<String, String> contactDetails,
        java.util.List<String> onCallSupport) {
}
