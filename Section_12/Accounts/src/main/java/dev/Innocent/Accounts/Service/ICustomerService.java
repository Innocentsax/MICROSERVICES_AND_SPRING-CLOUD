package dev.Innocent.Accounts.Service;

import dev.Innocent.Accounts.DTO.CustomerDetailsDTO;

public interface  ICustomerService {

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobile number
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
