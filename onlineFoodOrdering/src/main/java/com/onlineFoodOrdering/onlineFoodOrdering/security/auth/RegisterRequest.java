package com.onlineFoodOrdering.onlineFoodOrdering.security.auth;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Details of User
    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    // User
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //it refers to customer table's id in the user table
    //private Long userId;
}
