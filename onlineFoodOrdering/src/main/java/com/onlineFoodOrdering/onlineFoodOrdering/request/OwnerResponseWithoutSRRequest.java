package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OwnerResponseWithoutSRRequest {
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String gsm;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    public OwnerResponseWithoutSRRequest(Owner owner){
        this.firstName = owner.getDetailsOfUser().getFirstName();
        this.lastName = owner.getDetailsOfUser().getLastName();
        this.gender = owner.getDetailsOfUser().getGender();
        this.gsm = owner.getDetailsOfUser().getGsm();
        this.birthDate = owner.getDetailsOfUser().getBirthDate();
    }
}
