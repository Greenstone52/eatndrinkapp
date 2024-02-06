package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Card;
import lombok.Data;

@Data
public class CardResponse {
    private String cardNumber;
    private short cvc;
    private String cardTitle;
    private String date;
    private String cardOwner;

    public CardResponse(Card card){
        this.cardNumber = card.getCardNumber();
        this.cvc = card.getCvc();
        this.cardTitle = card.getName();
        this.date = card.getMonth()+"-"+card.getYear();
        this.cardOwner = card.getCustomer().getDetailsOfUser().getFirstName() + " "
                +card.getCustomer().getDetailsOfUser().getLastName();
    }
}
