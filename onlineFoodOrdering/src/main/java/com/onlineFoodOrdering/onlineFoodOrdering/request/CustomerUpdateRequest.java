package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerUpdateRequest {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
}
