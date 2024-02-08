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

    @GetMapping("/{id}")
    public List<CardResponse> getAllTheCardsOfTheCustomer(@PathVariable Long id){
        return cardService.getAllTheCardsOfTheCustomer(id);
    }

    @PostMapping("/{id}")
    public void setACard(@PathVariable Long id, @RequestBody CardCreateRequest card){
        cardService.setACard(id,card);
    }

    @PutMapping("/{id}/{cardNumber}")
    public void updateSelectedCard(@PathVariable Long id,@PathVariable String cardNumber,@RequestBody CardCreateRequest updateCard){
        cardService.updateSelectedCard(id,cardNumber,updateCard);
    }

    @PostMapping("/delete/{id}")
    public String deleteACard(@PathVariable Long id, @RequestBody CardDeleteRequest request){
        return cardService.deleteACard(id,request);
    }

}
