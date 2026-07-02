package com.bankApp.accountsMicroservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
@Entity
public class Account extends BaseEntity {
    @Id
    private Long accountNumber;

    private Long customerId;

    private String branchAddress;

    private String accountType;



}
