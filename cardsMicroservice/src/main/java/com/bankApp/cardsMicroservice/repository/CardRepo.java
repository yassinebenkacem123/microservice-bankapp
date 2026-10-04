package com.bankApp.cardsMicroservice.repository;

import com.bankApp.cardsMicroservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {

    Optional<Card> findByMobileNumber(String mobileNumber);

    Optional<Card> findByCardNumber(String cardNumber);
}
