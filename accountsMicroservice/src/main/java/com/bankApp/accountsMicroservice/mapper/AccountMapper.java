package com.bankApp.accountsMicroservice.mapper;

import com.bankApp.accountsMicroservice.dtos.AccountDTO;
import com.bankApp.accountsMicroservice.entity.Account;

public class AccountMapper {

    public static AccountDTO mapToAccountDTO(Account account, AccountDTO accountDTO){
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBranchAddress(account.getBranchAddress());
        return accountDTO;
    }

    public static Account mapToAccount(AccountDTO accountDTO, Account account){
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBranchAddress(accountDTO.getBranchAddress());
        return account;
    }
}
