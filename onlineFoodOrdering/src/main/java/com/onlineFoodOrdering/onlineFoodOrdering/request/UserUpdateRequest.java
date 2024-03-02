package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    //private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String gsm;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String birthDate;

}
