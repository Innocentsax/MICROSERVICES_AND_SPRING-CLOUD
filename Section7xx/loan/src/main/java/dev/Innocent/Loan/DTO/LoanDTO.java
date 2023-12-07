package dev.Innocent.Loan.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Loan",
        description = "Schema to hold loan information"
)
@Data
public class LoanDTO {

    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile number of customer",
            example = "8163093928"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digits")
    @Schema(
            description = "Loan number of customer",
            example = "231234897034"
    )
    private String loanNumber;

    @NotEmpty(message = "LoanType can not be null or empty")
    @Schema(
            description = "Type of the loan",
            example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount",
            example = "100000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid",
            example = "1000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than Zero")
    @Schema(
            description = "Total outstanding amount against a loan",
            example = "99000"
    )
    private int outstandingAmount;
}
