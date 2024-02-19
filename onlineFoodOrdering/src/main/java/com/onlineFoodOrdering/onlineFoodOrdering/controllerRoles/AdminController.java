package com.onlineFoodOrdering.onlineFoodOrdering.controllerRoles;

import com.onlineFoodOrdering.onlineFoodOrdering.response.ManAdminResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private ManagerAdminService managerAdminService;
    @GetMapping("/managers")
    public List<ManAdminResponse> getAllTheManagers(){
        return managerAdminService.getAllTheManagers();
    }

    @GetMapping("/admins")
    public List<ManAdminResponse> getAllTheAdmins(){
        return managerAdminService.getAllTheAdmins();
    }
}
