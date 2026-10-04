package com.bankApp.loansMicroservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold Error response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API path invoke by client"
    )
    private String apiPath;

    @Schema(
            description = "Error Code representing the error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the Error happened"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;
}
