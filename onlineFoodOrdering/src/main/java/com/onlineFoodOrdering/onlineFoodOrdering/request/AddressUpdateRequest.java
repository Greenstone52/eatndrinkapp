package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class AddressUpdateRequest {
    private String province;
    private String district;
    private String neighborhood;
    private String street;
    private int buildingNo;
    private int flatNo;
    private String addressTitle;
}
