package com.bankApp.accountsMicroservice.mapper;

import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.entity.Account;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class AccountMapperTest {

    @Test
    @DisplayName("Should map to account DTO")
    void shouldMapToAccountDTO(){
        Account account = new Account();

        account.setAccountNumber(100033L);
        account.setBranchAddress("Fes al madina");
        account.setCustomerId(1L);
        account.setAccountType("Savings");

        AccountDTO accountDTO = new AccountDTO();

        AccountDTO result = AccountMapper.mapToAccountDTO(account, accountDTO);

        AssertionsForClassTypes.assertThat(result).isNotNull();

        AssertionsForClassTypes.assertThat(result.getAccountNumber()).isEqualTo(account.getAccountNumber());
        AssertionsForClassTypes.assertThat(result.getBranchAddress()).isEqualTo(account.getBranchAddress());
        AssertionsForClassTypes.assertThat(result.getAccountType()).isEqualTo(account.getAccountType());

    }


    @Test
    @DisplayName("Should map to account entity")
    void shouldMapToAccountEntity() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBranchAddress("Ouislan street");
        accountDTO.setAccountNumber(20202L);
        accountDTO.setAccountType("Current");

        Account account = new Account();

        Account result = AccountMapper.mapToAccount(accountDTO, account);

        AssertionsForClassTypes.assertThat(result).isNotNull();

        AssertionsForClassTypes.assertThat(result.getAccountType()).isEqualTo(accountDTO.getAccountType());
        AssertionsForClassTypes.assertThat(result.getAccountNumber()).isEqualTo(accountDTO.getAccountNumber());
        AssertionsForClassTypes.assertThat(result.getBranchAddress()).isEqualTo(accountDTO.getBranchAddress());
    }
}