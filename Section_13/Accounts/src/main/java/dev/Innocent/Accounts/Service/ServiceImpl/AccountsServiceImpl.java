package dev.Innocent.Accounts.Service.ServiceImpl;

import dev.Innocent.Accounts.Constants.AccountsConstants;
import dev.Innocent.Accounts.DTO.AccountsDTO;
import dev.Innocent.Accounts.DTO.AccountsMsgDTO;
import dev.Innocent.Accounts.DTO.CustomerDTO;
import dev.Innocent.Accounts.Entity.Accounts;
import dev.Innocent.Accounts.Entity.Customer;
import dev.Innocent.Accounts.Exception.CustomerAlreadyExistsException;
import dev.Innocent.Accounts.Exception.ResourceNotFoundException;
import dev.Innocent.Accounts.Mapper.AccountsMapper;
import dev.Innocent.Accounts.Mapper.CustomerMapper;
import dev.Innocent.Accounts.Repository.AccountsRepository;
import dev.Innocent.Accounts.Repository.CustomerRepository;
import dev.Innocent.Accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private StreamBridge streamBridge;
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDTO - CustomerDTO Object
     */

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerDTO.getMobileNumber());
        }
        Customer savedCustomer =  customerRepository.save(customer);
        Accounts savedAccount =  accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts accounts, Customer customer){
        var accountsMsgDTO = new AccountsMsgDTO(accounts.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending communication request for the details: {}", accountsMsgDTO);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDTO);
        log.info("Is the communication request successfully processed ? : {}", result);
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

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobile Number
     */
    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));
        return customerDTO;
    }

    /**
     * @param customerDTO - CustomerDTO Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        if(accountsDTO != null){
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber",
                            accountsDTO.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDTO, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
