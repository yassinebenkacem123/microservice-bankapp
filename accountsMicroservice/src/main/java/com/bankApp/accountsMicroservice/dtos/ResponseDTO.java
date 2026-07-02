package com.bankApp.accountsMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseDTO {
    private String statusCode;
    private String statusMsg;

}
