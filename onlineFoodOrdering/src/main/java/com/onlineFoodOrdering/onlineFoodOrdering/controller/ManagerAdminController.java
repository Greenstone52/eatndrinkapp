package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.ManagerAdmin;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ManagerAdminRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.UserUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ManAdminResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class ManagerAdminController {
    private ManagerAdminService managerAdminService;
    @GetMapping("/managers")
    public List<ManAdminResponse> getAllTheManagers(){
        return managerAdminService.getAllTheManagers();
    }

    @GetMapping("/admins")
    public List<ManAdminResponse> getAllTheAdmins(){
        return managerAdminService.getAllTheAdmins();
    }

    @PostMapping("/managersAdmins")
    public void addOneManagerAdmin(@RequestBody AuthenticationRequest request){
        managerAdminService.addOneManagerAdmin(request);
    }

    @PutMapping("/managersAdmins/{id}")
    public String updateOneManagerAdmin(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return managerAdminService.updateOneManagerAdmin(id,request);
    }

    @PostMapping("/managersAdmins/{id}")
    public String deleteManagerAdmin(@PathVariable Long id, @RequestBody CustomerDeleteRequest request){
        return managerAdminService.deleteManagerAdmin(id,request);
    }

}
