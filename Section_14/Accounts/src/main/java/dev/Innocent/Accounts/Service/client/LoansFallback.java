package dev.Innocent.Accounts.Service.client;

import dev.Innocent.Accounts.DTO.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDTO> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
