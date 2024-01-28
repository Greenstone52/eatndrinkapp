package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import lombok.Data;

@Data
public class AddressResponse {
    private String province;
    private String district;
    private String neighborhood;
    private String street;
    private int buildingNo;
    private int flatNo;

    public AddressResponse(Address address){
        province = address.getProvince();
        district = address.getDistrict();
        neighborhood = address.getNeighborhood();
        street = address.getStreet();
        buildingNo = address.getBuildingNo();
        flatNo = address.getFlatNo();
    }
}
