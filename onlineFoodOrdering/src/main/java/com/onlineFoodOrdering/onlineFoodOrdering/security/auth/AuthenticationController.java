package com.onlineFoodOrdering.onlineFoodOrdering.security.auth;

import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
            return ResponseEntity.ok(authenticationService.register(request));
    }

    //public void register(@RequestBody RegisterRequest request){
//
    //    if(request.getRole().equals(Role.CUSTOMER)){
    //        customerService.addACustomer(request);
    //    }else if(request.getRole().equals(Role.OWNER)){
    //        ownerService.addOneOwner(request);
    //    }else if(request.getRole().equals(Role.ADMIN) || request.getRole().equals(Role.MANAGER)){
    //        managerAdminService.addOneManagerAdmin(request);
    //    }
    //}

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }
}
