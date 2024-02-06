package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Card;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CardRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CardResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;
    private CustomerRepository customerRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<CardResponse> getAllTheCardsOfTheCustomer(Long id){
        List<Card> cards = cardRepository.findCardByCustomerId(id);
        return cards.stream().map(card -> new CardResponse(card)).collect(Collectors.toList());
    }

    public void setACard(Long id, Card card){
        Customer customer = findCustomer(id);

        Card newCard = new Card();
        newCard.setCardNumber(card.getCardNumber());
        newCard.setCvc(card.getCvc());
        newCard.setName(card.getName());
        newCard.setYear(card.getYear());
        newCard.setMonth(card.getMonth());
        newCard.setCustomer(customer);

        cardRepository.save(newCard);
    }

    public void updateSelectedCard(Long id,String cardNumber,Card updateCard){
        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id,cardNumber).orElse(null);

        card.setCvc(updateCard.getCvc());
        card.setCardNumber(updateCard.getCardNumber());
        card.setYear(updateCard.getYear());
        card.setMonth(updateCard.getMonth());

        cardRepository.save(card);
    }


    //@Post kullan
    public String deleteACard(Long id, CardDeleteRequest request){
        //Customer customer = findCustomer(id);
//
        //CardDeleteRequest deletedCard = new CardDeleteRequest();
        //String no = request.getCardNumber();
        //deletedCard.setCardNumber(request.getCardNumber());
//
        //Card card = null;
//
        //if(customer != null){
        //    card = cardRepository.findCardByCustomerIdAndCardNumber(customer.getId(),deletedCard.getCardNumber()).orElse(null);
        //}else{
        //    return "There is no such a customer has this id.";
        //}
//
        //if(card != null){
        //    cardRepository.deleteById(card.getId());
        //    return "The card has this number "+ no + " was removed from the system.";
        //}else{
        //    return "There is no card has this card number";
        //}

        Customer customer = findCustomer(id);

        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id, request.getCardNumber()).orElse(null);

        if(card !=null){
            cardRepository.deleteById(card.getId());
            return "Your card is removed from the system successfully.";
        }else{
            return "There is no such a card enrolled in the system.";
        }
    }
}
