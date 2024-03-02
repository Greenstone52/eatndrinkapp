package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Card;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CardForbiddenValuesException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CardNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CardTitleAlreadyExistsException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CustomerNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CardRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CardResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if(findCustomer(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }else{
            List<Card> cards = cardRepository.findCardByCustomerId(id);
            return cards.stream().map(card -> new CardResponse(card)).collect(Collectors.toList());
        }

    }

    public void setACard(Long id, CardCreateRequest card){
        Customer customer = findCustomer(id);

        if(customer == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        Card card1 = cardRepository.findCardByCustomerIdAndName(id,card.getName()).orElse(null);

        if(card1 != null){
            throw new CardTitleAlreadyExistsException("You have already a card has this title.");
        }

        Card newCard = new Card();
        newCard.setCardNumber(card.getCardNumber());
        newCard.setCvc(card.getCvc());
        newCard.setName(card.getName());
        newCard.setYear(card.getYear());
        newCard.setMonth(card.getMonth());
        newCard.setCustomer(customer);

        try{
            cardRepository.save(newCard);
        }catch (ConstraintViolationException exception){
            throw new CardNotFoundException("Please enter appropriate values for the card.");
        }

    }

    public void updateSelectedCard(Long id,String cardNumber,CardCreateRequest updateCard){

        if(customerRepository.findById(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }else if(cardRepository.findCardByCustomerIdAndCardNumber(id,cardNumber) == null){
            throw new CardNotFoundException("You have no such a card has this card number.");
        }

        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id,cardNumber).orElse(null);

        if(card == null){
            throw new CardNotFoundException("There is no such a card.");
        }

        Card exCard = cardRepository.findCardByCustomerIdAndName(id, updateCard.getName()).orElse(null);

        if(exCard != null){
            throw new CardTitleAlreadyExistsException("You have already have a card has this title.");
        }

        //if(cardRepository.findCardByCustomerIdAndName(id,updateCard.getName()) != null){
        //    throw new CardTitleAlreadyExistsException("You have already have a card has this title.");
        //}

        card.setName(updateCard.getName());
        card.setCvc(updateCard.getCvc());
        card.setCardNumber(updateCard.getCardNumber());
        card.setYear(updateCard.getYear());
        card.setMonth(updateCard.getMonth());

        try{
            cardRepository.save(card);
        }catch (RuntimeException exception){
            throw new CardForbiddenValuesException("Please enter appropriate values for the card.");
        }

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

        //Customer customer = findCustomer(id);

        if(findCustomer(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id, request.getCardNumber()).orElse(null);

        if(card !=null){
            cardRepository.deleteById(card.getId());
            return "Your card is removed from the system successfully.";
        }else{
            throw new CardNotFoundException("There is no such a card.");
        }
    }
}
