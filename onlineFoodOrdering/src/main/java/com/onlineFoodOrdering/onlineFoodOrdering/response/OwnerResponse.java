package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class OwnerResponse {

    private DetailsOfUser detailsOfUser;
    private List<ShareRatio> shareRatios;
    public OwnerResponse(Owner owner){
        this.detailsOfUser = owner.getDetailsOfUser();
        this.shareRatios = owner.getShareRatios();
    }
}
