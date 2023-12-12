package dev.Innocent.Loan.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(description = "API path invoked by client")
    private String apiPath;

    @Schema(description = "Error code representing the error happened")
    private HttpStatus errorCode;

    @Schema(description = "Error message representing the error happened")
    private String errorMessage;

    @Schema(description = "Time representing when the error happened")
    private LocalDateTime errorTime;
}
