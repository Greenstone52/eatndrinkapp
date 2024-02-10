package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import lombok.Data;

import java.util.List;

@Data
public class RestauranCreateRequest {
    private String name;
    private String taxNo;
    private String password;
    private String type;

    //private List<Owner> owners;

    private String province;
    private String district;
}
