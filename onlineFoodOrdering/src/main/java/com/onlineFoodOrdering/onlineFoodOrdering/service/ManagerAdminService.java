package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.DetailsOfUser;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.ManagerAdmin;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.User;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.DetailsOfUserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ManagerAdminRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.UserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerAdminService {
    private ManagerAdminRepository managerAdminRepository;
    private UserRepository userRepository;
    private DetailsOfUserRepository detailsOfUserRepository;

    public void addOneManagerAdmin(AuthenticationRequest request){
        ManagerAdmin newManagerAdmin = new ManagerAdmin();
        User user = new User();
        DetailsOfUser detailsOfUser = new DetailsOfUser();

        //newOwner.setBankAccount(owner.getBankAccount());
        //newOwner.setRestaurants(owner.getRestaurants());

        Long userCount = userRepository.count();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setUserDetailsId(managerAdminRepository.count() + 1);
        userRepository.save(user);

        if(userRepository.count() >= userCount){
            //newOwner.setUsername(owner.getUsername());
            detailsOfUser.setGsm(request.getGsm());
            detailsOfUser.setGender(request.getGender());
            detailsOfUser.setLastName(request.getLastName());
            detailsOfUser.setFirstName(request.getFirstName());
            detailsOfUser.setBirthDate(request.getBirthDate());
            detailsOfUserRepository.save(detailsOfUser);
            newManagerAdmin.setDetailsOfUser(detailsOfUser);
        }

        newManagerAdmin.setUser(user);
        managerAdminRepository.save(newManagerAdmin);
    }
}
