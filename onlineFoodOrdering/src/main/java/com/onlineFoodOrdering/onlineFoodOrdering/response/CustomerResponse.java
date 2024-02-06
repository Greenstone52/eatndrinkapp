package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private Gender gender;
    private String gsm;
    private LocalDate birthDate;
    private double totalSpendMoney;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private int totalNumberOfOrder;

    public CustomerResponse(Customer customer){
        id = customer.getId();
        name = customer.getDetailsOfUser().getFirstName()+ " " + customer.getDetailsOfUser().getLastName();
        gender = customer.getDetailsOfUser().getGender();
        gsm = customer.getDetailsOfUser().getGsm();
        birthDate = customer.getDetailsOfUser().getBirthDate();
        totalSpendMoney = customer.getTotalSpendMoney();
        totalNumberOfOrder = customer.getTotalNumberOfOrder();
    }
}
