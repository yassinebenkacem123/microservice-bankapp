package com.bankApp.cardsMicroservice.dto;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "cards")
//@RefreshScope
public class CardsContactInfoDTO{
    String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;

}
