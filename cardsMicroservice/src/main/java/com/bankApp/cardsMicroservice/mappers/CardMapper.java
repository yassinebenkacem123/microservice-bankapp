package com.bankApp.cardsMicroservice.mappers;

import com.bankApp.cardsMicroservice.dto.CardDTO;
import com.bankApp.cardsMicroservice.entity.Card;

public class CardMapper {
    public static CardDTO mapToCardsDto(Card cards, CardDTO cardsDto) {
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        return cardsDto;
    }

    public static Card mapToCards(CardDTO cardsDto, Card cards) {
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        return cards;
    }
}
