package com.bankApp.accountsMicroservice.repository;

import com.bankApp.accountsMicroservice.entity.Account;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;



@DataJpaTest
class AccountRepoTest {

    @Autowired
    private AccountRepo accountRepo;

    @TestConfiguration
    static  class TesTAuditConfig{

        @Bean(name = "auditAwareImpl")
        AuditorAware<String> auditorAware(){
            return () -> Optional.of("test-user");
        }
    }


    @BeforeEach
    public void setUp(){
        Account account = new Account();
        account.setAccountNumber(123456789L);
        account.setCustomerId(1L);
        account.setBranchAddress("123 Main St");
        account.setAccountType("Savings");
        accountRepo.save(account);
    }

    /**
     * Test The findAccountByCustomerId method
     */

    @Test
    @DisplayName("Should find account by customer id")
    public void shouldFindAccountByCustomerId()
    {
        Optional<Account> result = accountRepo.findByCustomerId(1L);
        AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    @DisplayName("Should not find account by customer id")
    public void shouldNotFindAccountByCustomerId(){

        Optional<Account> result = accountRepo.findByCustomerId(2L);
        AssertionsForClassTypes.assertThat(result).isEmpty();
    }

    /**
     *  Test the deleteByCustomerId method
     */

    @Test
    @DisplayName("Should delete account by customer id")
    public void shouldDeleteAccountByCustomerId(){
        accountRepo.deleteByCustomerId(1L);

        Optional<Account> result = accountRepo.findByCustomerId(1L);

        AssertionsForClassTypes.assertThat(result).isNotPresent();
    }


    @Test
    @DisplayName("Should not delete account By customer Id")
    public void shouldNotDeleteAccountByCustomerId(){
        accountRepo.deleteByCustomerId(2L);

        Optional<Account> result = accountRepo.findByCustomerId(1L);
        AssertionsForClassTypes.assertThat(result).isPresent();
    }












}