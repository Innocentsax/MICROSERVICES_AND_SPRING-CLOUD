package dev.Innocent.Loan.Service.ServicesImpl;

import dev.Innocent.Loan.Constants.LoanConstants;
import dev.Innocent.Loan.DTO.LoanDTO;
import dev.Innocent.Loan.Entity.Loans;
import dev.Innocent.Loan.Exception.LoanAlreadyExistsException;
import dev.Innocent.Loan.Exception.ResourceNotFoundException;
import dev.Innocent.Loan.Mapper.LoanMapper;
import dev.Innocent.Loan.Repository.LoansRepository;
import dev.Innocent.Loan.Service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;
    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException(
                    "Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber){
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     * @param mobileNumber -Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoanDTO fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoanMapper.mapToLoanDTO(loans, new LoanDTO());
    }

    /**
     * @param loanDTO - LoansDTO Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateLoan(LoanDTO loanDTO) {
        Loans loans = loansRepository.findByLoanNumber(loanDTO.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loanDTO.getLoanNumber())
        );
        LoanMapper.mapToLoans(loanDTO, loans);
        loansRepository.save(loans);
        return true;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if  delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
