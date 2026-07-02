package com.bankApp.accountsMicroservice.repository;

import com.bankApp.accountsMicroservice.entity.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepoTest {

    @Autowired
    CustomerRepo customerRepo;

    @TestConfiguration
    static  class TesTAuditConfig{
        @Bean(name = "auditAwareImpl")
        AuditorAware<String> auditorAware(){
            return () -> Optional.of("test-user");
        }
    }



    @BeforeEach
    public void setUp(){
        Customer customer1 = new Customer();

        customer1.setEmail("yassine@gmail.com");
        customer1.setName("yassine ben kacem");
        customer1.setMobileNumber("0610833077");

        Customer customer2 = new Customer();
        customer2.setEmail("ahmed@gmail.com");
        customer2.setName("ahmed kloch");
        customer2.setMobileNumber("020303030");

        List<Customer> customers = new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);

        customerRepo.saveAll(customers);
    }


    @Test
    @DisplayName("Should Find Customer by Mobile Number")
    public void shouldFindCustomerByMobileNumber(){

        Optional<Customer> result = customerRepo.findByMobileNumber("0610833077");

        AssertionsForClassTypes.assertThat(result).isPresent();

    }



    @Test
    @DisplayName("Should Not Find Customer By Mobile Number")
    public void souldNotFindCustomerByMobileNumber(){

        Optional<Customer> result = customerRepo.findByMobileNumber("0000000000");

        AssertionsForClassTypes.assertThat(result).isNotPresent();

    }


}