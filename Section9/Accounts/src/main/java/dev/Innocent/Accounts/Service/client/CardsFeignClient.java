package dev.Innocent.Accounts.Service.client;

import dev.Innocent.Accounts.DTO.CardsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "card")
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<CardsDTO> fetchCardDetails(@RequestHeader("udobank-correlation-id")
                                                         String correlationId, @RequestParam String mobileNumber);
}
