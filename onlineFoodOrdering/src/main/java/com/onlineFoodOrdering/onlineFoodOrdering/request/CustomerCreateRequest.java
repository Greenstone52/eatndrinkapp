package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.foreign.SegmentScope;
import java.time.LocalDate;

@Data
public class CustomerCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String gsm;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
}
