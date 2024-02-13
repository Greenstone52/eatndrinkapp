package com.onlineFoodOrdering.onlineFoodOrdering.response;


import com.onlineFoodOrdering.onlineFoodOrdering.entity.ManagerAdmin;
import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

public class ManAdminResponse {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;
    private LocalDate birthDate;

    public ManAdminResponse(ManagerAdmin manager){
        this.firstName = manager.getDetailsOfUser().getFirstName();
        this.lastName = manager.getDetailsOfUser().getLastName();
        this.gender = manager.getDetailsOfUser().getGender();
        this.gsm = manager.getDetailsOfUser().getGsm();
        this.birthDate = manager.getDetailsOfUser().getBirthDate();
    }
}
