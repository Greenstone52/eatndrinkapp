package com.onlineFoodOrdering.onlineFoodOrdering.security.auth;

import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private OwnerService ownerService;
    private CustomerService customerService;
    private ManagerAdminService managerAdminService;

    @PostMapping
    public void register(@RequestBody AuthenticationRequest request){

        if(request.getRole().equals(Role.CUSTOMER)){
            customerService.addACustomer(request);
        }else if(request.getRole().equals(Role.OWNER)){
            ownerService.addOneOwner(request);
        }else if(request.getRole().equals(Role.ADMIN) || request.getRole().equals(Role.MANAGER)){
            managerAdminService.addOneManagerAdmin(request);
        }
    }

    public AuthenticationResponse authenticate(){
        return null;
    }

    public AuthenticationResponse refreshToken(){
        return null;
    }
}
