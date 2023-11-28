package dev.Innocent.Accounts.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity{

    @Column(name = "customer_id")
    private Long customerId;
    
    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;
}
