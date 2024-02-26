package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantShareRatio;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class OwnerResponse {
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String gsm;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    //private List<ShareRatio> shareRatios;
    private List<RestaurantShareRatio> resRatios;
    private double balance;
    public OwnerResponse(Owner owner){
        this.firstName = owner.getDetailsOfUser().getFirstName();
        this.lastName = owner.getDetailsOfUser().getLastName();
        this.gender = owner.getDetailsOfUser().getGender();
        this.gsm = owner.getDetailsOfUser().getGsm();
        this.birthDate = owner.getDetailsOfUser().getBirthDate();
        this.balance = owner.getBalance();
    }
}
