package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Card;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CardResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private CardService cardService;

    @GetMapping("/{customerId}")
    public List<CardResponse> getAllTheCardsOfTheCustomer(@PathVariable Long customerId){
        return cardService.getAllTheCardsOfTheCustomer(customerId);
    }

    @PostMapping("/{customerId}")
    public void setACard(@PathVariable Long customerId, @RequestBody CardCreateRequest card){
        cardService.setACard(customerId,card);
    }

    @PutMapping("/{customerId}/{cardNumber}")
    public void updateSelectedCard(@PathVariable Long customerId,@PathVariable String cardNumber,@RequestBody CardCreateRequest updateCard){
        cardService.updateSelectedCard(customerId,cardNumber,updateCard);
    }

    @PostMapping("/delete/{customerId}")
    public String deleteACard(@PathVariable Long customerId, @RequestBody CardDeleteRequest request){
        return cardService.deleteACard(customerId,request);
    }

}
