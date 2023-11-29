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

    /**
     * @param customerDTO - CustomerDTO Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDTO customerDTO);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
