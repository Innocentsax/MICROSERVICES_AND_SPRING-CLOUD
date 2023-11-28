package dev.Innocent.Accounts.Service;

import dev.Innocent.Accounts.DTO.CustomerDTO;

public interface IAccountsService {
    /**
     * @param customerDTO - CustomerDTO Object
     */
    void createAccount(CustomerDTO customerDTO);
}
