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
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private CustomerRepo customerRepo;



    @InjectMocks
    private AccountServiceImpl underTest;

    private Customer customer;
    private Account account;
    private CustomerDTO customerDTO;
    private AccountDTO accountDTO;


    @BeforeEach
    void setUp(){
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("yassine");
        customer.setEmail("yassine@gmail.com");
        customer.setMobileNumber("0610833077");
        account = new Account();
        account.setAccountNumber(123456789L);
        account.setCustomerId(1L);
        account.setAccountType(AccountConstats.SAVINGS);
        account.setBranchAddress(AccountConstats.ADDRESS);

        accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(123456789L);
        accountDTO.setAccountType(AccountConstats.SAVINGS);
        accountDTO.setBranchAddress(AccountConstats.ADDRESS);

        customerDTO = new CustomerDTO();
        customerDTO.setName("Yassine");
        customerDTO.setEmail("yassine@gmail.com");
        customerDTO.setMobileNumber("0612345678");
        customerDTO.setAccountDTO(accountDTO);
    }

    /**
     * Test related with createAccount Method.
     */

    @Test
    @DisplayName("createAccount: Should create account when customer does not exist")
    void shouldCreateAccount(){
        // mock set up.
        Mockito.when(customerRepo.findByMobileNumber(customerDTO.getMobileNumber())).thenReturn(Optional.empty());

        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);

        Mockito.when(accountRepo.save(Mockito.any(Account.class))).thenReturn(account);


        underTest.createAccount(customerDTO);

        Mockito.verify(customerRepo).findByMobileNumber(customerDTO.getMobileNumber());
        Mockito.verify(customerRepo).save(Mockito.any(Customer.class));
        Mockito.verify(accountRepo).save(Mockito.any(Account.class));

        Mockito.verifyNoMoreInteractions(customerRepo, accountRepo);


    }

    @Test
    @DisplayName("createAccount:  should throw CustomerAlreadyExistException when mobile number already exists")
    void shouldThrowExceptionWhenMobileNumberAlreadyExists(){
        Mockito.when(customerRepo.findByMobileNumber(customerDTO.getMobileNumber()))
                .thenReturn(Optional.of(customer));

        Assertions.assertThrows(CustomerAlreadyExistException.class, ()->{
            underTest.createAccount(customerDTO);

        });

        Mockito.verify(customerRepo).findByMobileNumber(customerDTO.getMobileNumber());
        Mockito.verify(customerRepo, Mockito.never()).save(Mockito.any(Customer.class));

        Mockito.verifyNoInteractions(accountRepo);
    }

    /**
     * Test related with getAccount :
     */
    @Test
    @DisplayName("getAccount : Should return customer with account when mobile number exists")
    void shouldReturnCustomerWithAccountWhenMobileNumberExists(){
        Mockito.when(customerRepo.findByMobileNumber("0610833077")).thenReturn(Optional.of(customer));
        Mockito.when(accountRepo.findByCustomerId(customer.getCustomerId())).
                thenReturn(Optional.of(account));

        CustomerDTO result = underTest.getAccount("0610833077");
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result.getName()).isEqualTo("yassine");
        AssertionsForClassTypes.assertThat(result.getEmail()).isEqualTo("yassine@gmail.com");
        AssertionsForClassTypes.assertThat(result.getMobileNumber()).isEqualTo("0610833077");

        AssertionsForClassTypes.assertThat(result.getAccountDTO()).isNotNull();
        AssertionsForClassTypes.assertThat(result.getAccountDTO().getAccountNumber()).isEqualTo(123456789L);
        AssertionsForClassTypes.assertThat(result.getAccountDTO().getAccountType()).isEqualTo(AccountConstats.SAVINGS);
        AssertionsForClassTypes.assertThat(result.getAccountDTO().getBranchAddress()).isEqualTo(AccountConstats.ADDRESS);

        Mockito.verify(customerRepo).findByMobileNumber("0610833077");
        Mockito.verify(accountRepo).findByCustomerId(1L);
        Mockito.verifyNoMoreInteractions(customerRepo, accountRepo);
    }

    @Test
    @DisplayName("getAccount : Should throw ResourceNotFoundException when mobile number does not exist")
    void shouldThrowExceptionWhenMobileNumberDoesNotExist() {
        Mockito.when(customerRepo.findByMobileNumber("0610833077"))
                .thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.getAccount("0610833077");
        });

        Mockito.verify(customerRepo).findByMobileNumber("0610833077");
        Mockito.verifyNoInteractions(accountRepo);
    }

    @Test
    @DisplayName(
            "getAccount : Should throw ResourceNotFoundException when account does not exist for existing customer"
    )
    void shouldThrowExceptionWhenAccountDoesNotExistForExistingCustomer() {
        Mockito.when(customerRepo.findByMobileNumber("0610833077"))
                .thenReturn(Optional.of(customer));

        Mockito.when(accountRepo.findByCustomerId(customer.getCustomerId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.getAccount("0610833077");
        });

        Mockito.verify(customerRepo).findByMobileNumber("0610833077");
        Mockito.verify(accountRepo).findByCustomerId(1L);
    }

    @Test
    @DisplayName(
            "updateAccount : should return false when accountDTO is null"
    )
    void shouldReturnFalseWhenAccountDTOIsNull() {
        customerDTO.setAccountDTO(null);

        boolean result = underTest.updateAccount(customerDTO);

        AssertionsForClassTypes.assertThat(result).isFalse();

        Mockito.verifyNoInteractions(accountRepo);
    }

    @Test
    @DisplayName(
            "updateAccount: should return true when account is updated successfully"
    )
    void shouldReturnTrueWhenAccountIsUpdatedSuccessfully() {
        Mockito.when(accountRepo.findById(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepo.save(Mockito.any(Account.class)))
                .thenReturn(account);

        Mockito.when(customerRepo.findById(account.getCustomerId()))
                .thenReturn(Optional.of(customer));

        Mockito.when(customerRepo.save(Mockito.any(Customer.class)))
                .thenReturn(customer);

        boolean result = underTest.updateAccount(customerDTO);

        AssertionsForClassTypes.assertThat(result).isTrue();

        Mockito.verify(accountRepo).findById(accountDTO.getAccountNumber());
        Mockito.verify(accountRepo).save(Mockito.any(Account.class));
        Mockito.verify(customerRepo).findById(account.getCustomerId());
        Mockito.verify(customerRepo).save(Mockito.any(Customer.class));
    }

    @Test
    @DisplayName("updateAccount: should throw ResourceNotFoundException when account does not exist")
    void shouldThrowExceptionWhenAccountDoesNotExistInUpdate() {
        Mockito.when(accountRepo.findById(accountDTO.getAccountNumber()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.updateAccount(customerDTO);
        });

        Mockito.verify(accountRepo).findById(accountDTO.getAccountNumber());
        Mockito.verify(accountRepo, Mockito.never()).save(Mockito.any(Account.class));
    }

    @Test
    @DisplayName("updateAccount: should throw ResourceNotFoundException when customer does not exist")
    void shouldThrowExceptionWhenCustomerDoesNotExistInUpdate() {
        Mockito.when(accountRepo.findById(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepo.save(Mockito.any(Account.class)))
                .thenReturn(account);

        Mockito.when(customerRepo.findById(account.getCustomerId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.updateAccount(customerDTO);
        });

        Mockito.verify(accountRepo).findById(accountDTO.getAccountNumber());
        Mockito.verify(customerRepo).findById(account.getCustomerId());
        Mockito.verify(customerRepo, Mockito.never()).save(Mockito.any(Customer.class));
    }

    /**
     * Test related with deleteAccount :
     */
    @Test
    @DisplayName("deleteAccount: should return true when account is deleted successfully")
    void shouldReturnTrueWhenAccountIsDeletedSuccessfully() {
        Mockito.when(customerRepo.findByMobileNumber("0610833077"))
                .thenReturn(Optional.of(customer));

        boolean result = underTest.deleteAccount("0610833077");

        AssertionsForClassTypes.assertThat(result).isTrue();

        Mockito.verify(customerRepo).findByMobileNumber("0610833077");
        Mockito.verify(customerRepo).delete(customer);
        Mockito.verify(accountRepo).deleteByCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("deleteAccount: should throw ResourceNotFoundException when customer does not exist")
    void shouldThrowExceptionWhenCustomerDoesNotExistInDelete() {
        Mockito.when(customerRepo.findByMobileNumber("0610833077"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.deleteAccount("0610833077");
        });

        Mockito.verify(customerRepo).findByMobileNumber("0610833077");
        Mockito.verify(customerRepo, Mockito.never()).delete(Mockito.any(Customer.class));
        Mockito.verifyNoInteractions(accountRepo);
    }
}