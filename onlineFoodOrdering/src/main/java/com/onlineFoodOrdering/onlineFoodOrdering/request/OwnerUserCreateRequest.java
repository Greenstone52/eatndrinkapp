package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OwnerUserCreateRequest {
    private String email;
    private String password;

    //
    private Long userDetailsId;

    private Role role = Role.CUSTOMER;
    //
    private Long userId;

  //  private DetailsOfUser detailsOfUser;

    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
}
