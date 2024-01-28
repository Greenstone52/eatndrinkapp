package com.onlineFoodOrdering.onlineFoodOrdering.request;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CardUpdateRequest {
    @Length(min = 16,max = 16)
    @Column(unique = true)
    private String cardNumber;

    @Length(min = 3,max = 3)
    private short cvc;
    private short month;
    private short year;
}
