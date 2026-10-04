package com.bankApp.loansMicroservice.dtos;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "loans")
//@RefreshScope
public class LoanContactInfoDTO {
    private String message;
    private java.util.Map<String, String> contactDetails;
    private java.util.List<String> onCallSupport;
}
