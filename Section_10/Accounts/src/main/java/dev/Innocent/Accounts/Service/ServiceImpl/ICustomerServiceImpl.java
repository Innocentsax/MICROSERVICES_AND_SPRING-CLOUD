package dev.Innocent.Accounts.Service.ServiceImpl;

import dev.Innocent.Accounts.DTO.AccountsDTO;
import dev.Innocent.Accounts.DTO.CardsDTO;
import dev.Innocent.Accounts.DTO.CustomerDetailsDTO;
import dev.Innocent.Accounts.DTO.LoanDTO;
import dev.Innocent.Accounts.Entity.Accounts;
import dev.Innocent.Accounts.Entity.Customer;
import dev.Innocent.Accounts.Exception.ResourceNotFoundException;
import dev.Innocent.Accounts.Mapper.AccountsMapper;
import dev.Innocent.Accounts.Mapper.CustomerMapper;
import dev.Innocent.Accounts.Repository.AccountsRepository;
import dev.Innocent.Accounts.Repository.CustomerRepository;
import dev.Innocent.Accounts.Service.ICustomerService;
import dev.Innocent.Accounts.Service.client.CardsFeignClient;
import dev.Innocent.Accounts.Service.client.LoansFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ICustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Autowired
    public ICustomerServiceImpl(AccountsRepository accountsRepository,
                                CustomerRepository customerRepository,
                                CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobile number
     */
    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));

        ResponseEntity<LoanDTO> loanDTOResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null != loanDTOResponseEntity){
            customerDetailsDTO.setLoanDTO(loanDTOResponseEntity.getBody());
        }

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(null != loanDTOResponseEntity){
            customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());
        }

        return customerDetailsDTO;
    }
}
