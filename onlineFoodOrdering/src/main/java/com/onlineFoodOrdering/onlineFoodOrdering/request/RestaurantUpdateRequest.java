package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantUpdateRequest {

    private String name;
    private String type;

    private String taxNo;

    private String province;
    private String district;


}
