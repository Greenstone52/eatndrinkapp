package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Card;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CardDeleteRequest;
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

    @GetMapping("/{id}")
    public List<CardResponse> getAllTheCardsOfTheCustomer(@PathVariable Long id){
        return cardService.getAllTheCardsOfTheCustomer(id);
    }

    @PostMapping("/{id}")
    public void setACard(@PathVariable Long id, @RequestBody Card card){
        cardService.setACard(id,card);
    }

    @PutMapping("/{id}/{cardNumber}")
    public void updateSelectedCard(@PathVariable Long id,@PathVariable String cardNumber,@RequestBody Card updateCard){
        cardService.updateSelectedCard(id,cardNumber,updateCard);
    }

    @DeleteMapping("/{id}")
    public void deleteACard(Long id, CardDeleteRequest request){
        cardService.deleteACard(id,request);
    }

}
