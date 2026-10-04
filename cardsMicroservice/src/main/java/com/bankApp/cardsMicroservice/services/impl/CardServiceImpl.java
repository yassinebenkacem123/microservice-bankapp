package com.bankApp.cardsMicroservice.services.impl;

import com.bankApp.cardsMicroservice.constants.CardConstants;
import com.bankApp.cardsMicroservice.dto.CardDTO;
import com.bankApp.cardsMicroservice.entity.Card;
import com.bankApp.cardsMicroservice.execption.CardAlreadyExistsException;
import com.bankApp.cardsMicroservice.execption.ResourceNotFoundException;
import com.bankApp.cardsMicroservice.mappers.CardMapper;
import com.bankApp.cardsMicroservice.repository.CardRepo;
import com.bankApp.cardsMicroservice.services.CardServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardServices {
    private CardRepo cardRepo;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCards = cardRepo.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardRepo.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardDTO fetchCard(String mobileNumber) {
        Card card = cardRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardMapper.mapToCardsDto(card, new CardDTO());
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardDTO cardsDto) {
        Card cards = cardRepo.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardMapper.mapToCards(cardsDto, cards);
        cardRepo.save(cards);
        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardRepo.deleteById(cards.getCardId());
        return true;
    }


}
