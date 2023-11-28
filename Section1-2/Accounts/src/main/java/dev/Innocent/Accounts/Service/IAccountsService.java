package dev.Innocent.Accounts.Service;

import dev.Innocent.Accounts.DTO.CustomerDTO;

public interface IAccountsService {
    /**
     * @param customerDTO - CustomerDTO Object
     */
    void createAccount(CustomerDTO customerDTO);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobile Number
     */
    CustomerDTO fetchAccount(String mobileNumber);
}
