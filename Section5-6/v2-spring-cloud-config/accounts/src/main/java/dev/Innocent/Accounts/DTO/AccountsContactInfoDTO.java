package dev.Innocent.Accounts.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "accounts")
public class AccountsContactInfoDTO {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
