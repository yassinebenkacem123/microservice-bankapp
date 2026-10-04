package com.bankApp.accountsMicroservice.dtos;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "accounts")
//@RefreshScope
public class AccountsContactInfoDTO {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;


}