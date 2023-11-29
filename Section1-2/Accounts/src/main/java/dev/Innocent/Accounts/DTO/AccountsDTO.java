package dev.Innocent.Accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDTO {


    @NotEmpty(message = "Account Number can not be null or empty")
    @Pattern(regexp = "($|[0-9]{10})", message = "Account Number must be 10 digits")
    @Schema(
            description = "Account Number of InnocentUdo's Bank",
            example = "2346578564"
    )
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be null or empty")
    @Schema(
            description = "Account Type of InnocentUdo's Bank",
            example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch Address can not be null or empty")
    @Schema(
            description = "InnocentUdo's Branch address",
            example = "Lekki Phase 1, Lagos Nigeria"
    )
    private String branchAddress;
}
