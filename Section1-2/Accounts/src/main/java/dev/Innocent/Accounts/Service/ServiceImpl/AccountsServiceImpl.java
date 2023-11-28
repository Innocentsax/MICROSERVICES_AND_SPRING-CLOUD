package dev.Innocent.Accounts.Service.ServiceImpl;

import dev.Innocent.Accounts.Constants.AccountsConstants;
import dev.Innocent.Accounts.DTO.CustomerDTO;
import dev.Innocent.Accounts.Entity.Accounts;
import dev.Innocent.Accounts.Entity.Customer;
import dev.Innocent.Accounts.Mapper.CustomerMapper;
import dev.Innocent.Accounts.Repository.AccountsRepository;
import dev.Innocent.Accounts.Repository.CustomerRepository;
import dev.Innocent.Accounts.Service.IAccountsService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDTO - CustomerDTO Object
     */

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Customer savedCustomer =  customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
