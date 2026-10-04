package com.bankApp.accountsMicroservice.functions;

import com.bankApp.accountsMicroservice.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


@Configuration
public class AccountFunction {

    private final static Logger logger = LoggerFactory.getLogger(AccountFunction.class);


    @Bean
    public Consumer<Long> updateCommunication(AccountService accountService){
        return accountNumber -> {
            logger.info("Updating communication status for the account number : "+ accountNumber.toString());
            accountService.updateCommunicationStatus(accountNumber);
        };
    }

}
