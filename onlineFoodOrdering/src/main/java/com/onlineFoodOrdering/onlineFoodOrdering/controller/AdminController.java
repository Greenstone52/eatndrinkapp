package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.response.ManAdminResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private ManagerAdminService managerAdminService;
    private OwnerService ownerService;
    @GetMapping("/managers")
    public List<ManAdminResponse> getAllTheManagers(){
        return managerAdminService.getAllTheManagers();
    }

    @GetMapping("/admins")
    public List<ManAdminResponse> getAllTheAdmins(){
        return managerAdminService.getAllTheAdmins();
    }

    // Owners
    @GetMapping("/owners/top5")
    public List<OwnerResponse> getTopFiveOwners(){
        return ownerService.getTopFiveOwners();
    }

    @GetMapping("/owners/top10")
    public List<OwnerResponse> getTop10Owners(){
        return ownerService.getTop10Owners();
    }

    @GetMapping("/owners/{topN}")
    public List<OwnerResponse> getTopNOwners(@PathVariable Long topN){
        return ownerService.getTopNOwners(topN);
    }
}
