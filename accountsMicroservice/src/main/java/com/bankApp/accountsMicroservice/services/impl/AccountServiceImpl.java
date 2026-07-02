package com.bankApp.accountsMicroservice.services.impl;

import com.bankApp.accountsMicroservice.constants.AccountConstats;
import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.dtos.CustomerDTO;
import com.bankApp.accountsMicroservice.entity.Account;
import com.bankApp.accountsMicroservice.entity.Customer;
import com.bankApp.accountsMicroservice.exceptions.CustomerAlreadyExistException;
import com.bankApp.accountsMicroservice.exceptions.ResourceNotFoundException;
import com.bankApp.accountsMicroservice.mapper.AccountMapper;
import com.bankApp.accountsMicroservice.mapper.CustomerMapper;
import com.bankApp.accountsMicroservice.repository.AccountRepo;
import com.bankApp.accountsMicroservice.repository.CustomerRepo;
import com.bankApp.accountsMicroservice.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepo accountRepo;

    private CustomerRepo customerRepo;
    /**
     *
     * @param customerDTO
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());

        // check this customer already exist.
        Optional<Customer> customerOptional = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(customerOptional.isPresent()){
            throw new CustomerAlreadyExistException(
                    "Customer with mobile number " + customer.getMobileNumber() + " already exist."
            );
        }

        // Else :
        // save customer.
        Customer credtedCustomer = customerRepo.save(customer);

        // create account for this customer.
        Account customerAccount = new Account();
        customerAccount.setCustomerId(credtedCustomer.getCustomerId());
        long radomNumber = 1000000L + new Random().nextInt(90000);

        customerAccount.setAccountType(AccountConstats.SAVINGS);
        customerAccount.setAccountNumber(radomNumber);
        customerAccount.setBranchAddress(AccountConstats.ADDRESS);

        // save account.
        accountRepo.save(customerAccount);
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDTO getAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account customerAccount = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
        );

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(new CustomerDTO(), customer);
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDTO(customerAccount, new AccountDTO()));

        return  customerDTO;
    }

    /**
     * @param customerDTO
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean accountUpdated = false;

        AccountDTO accountDTO = customerDTO.getAccountDTO();
        if(accountDTO != null){
            Account accountToUpdate = accountRepo.findById(accountDTO.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "accountNumber", accountDTO.getAccountNumber().toString())
            );
            Account updatedAccount =  accountRepo.save(AccountMapper.mapToAccount(accountDTO, accountToUpdate));

            Customer customerToUpdate = customerRepo.findById(updatedAccount.getCustomerId()).orElseThrow(
                    ()-> new ResourceNotFoundException(
                            "Customer",
                            "CustomerId",
                            updatedAccount.getCustomerId().toString()
                    )
            );
            customerRepo.save(CustomerMapper.mapToCustomer(customerDTO, customerToUpdate));
            accountUpdated = true;

        }

        return accountUpdated;
    }


    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customerToDelete = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        customerRepo.delete(customerToDelete);

        accountRepo.deleteByCustomerId(customerToDelete.getCustomerId());

        return true;
    }
}
