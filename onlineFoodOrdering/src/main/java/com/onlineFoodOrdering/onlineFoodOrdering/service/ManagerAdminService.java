package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.DetailsOfUser;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.ManagerAdmin;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.User;
import com.onlineFoodOrdering.onlineFoodOrdering.enums.Gender;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.IncorrectPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.InvalidValueException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.UserForbiddenValuesException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.UserNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.DetailsOfUserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ManagerAdminRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.UserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.InvalidPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.request.UserUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ManAdminResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.RegisterRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManagerAdminService {
    private ManagerAdminRepository managerAdminRepository;
    private UserRepository userRepository;
    private DetailsOfUserRepository detailsOfUserRepository;
    private PasswordEncoder passwordEncoder;

    public List<ManAdminResponse> getAllTheManagers(){
        List<ManagerAdmin> managers = managerAdminRepository.findAll();
        List<ManagerAdmin> result = new ArrayList<>();

        for (int i = 0; i < managerAdminRepository.count(); i++) {
            if(managers.get(i).getUser().getRole().equals(Role.MANAGER)){
                result.add(managers.get(i));
            }
        }

        return result.stream().map(manager -> new ManAdminResponse(manager)).collect(Collectors.toList());
    }

    public List<ManAdminResponse> getAllTheAdmins(){
        List<ManagerAdmin> admins = managerAdminRepository.findAll();
        List<ManagerAdmin> result = new ArrayList<>();

        for (int i = 0; i < managerAdminRepository.count(); i++) {
            if(admins.get(i).getUser().getRole().equals(Role.ADMIN)){
                result.add(admins.get(i));
            }
        }

        return result.stream().map(admin -> new ManAdminResponse(admin)).collect(Collectors.toList());
    }

    public void addOneManagerAdmin(RegisterRequest request){
        ManagerAdmin newManagerAdmin = new ManagerAdmin();
        User user = new User();
        DetailsOfUser detailsOfUser = new DetailsOfUser();

        //newOwner.setBankAccount(owner.getBankAccount());
        //newOwner.setRestaurants(owner.getRestaurants());

        Long userCount = userRepository.count();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        //user.setUserDetailsId(managerAdminRepository.count() + 1);
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

        List<ManagerAdmin> managerAdmins = managerAdminRepository.findAll();
        ManagerAdmin lastManAdmin = managerAdmins.get(managerAdmins.size()-1);

        user.setUserDetailsId(lastManAdmin.getId());
        userRepository.save(user);
        newManagerAdmin.setUser(user);
        managerAdminRepository.save(newManagerAdmin);

    }

    public String updateOneManagerAdmin(Long id, UserUpdateRequest request){
        ManagerAdmin manAdmin = managerAdminRepository.findById(id).orElseThrow(()->new UserNotFoundException("There is no such an user."));

        boolean isAdmin = false;
        User user;


        user = userRepository.findUserByUserDetailsIdAndRole(manAdmin.getId(),Role.MANAGER);

        if(user == null){
            user = userRepository.findUserByUserDetailsIdAndRole(manAdmin.getId(),Role.ADMIN);
            isAdmin = true;
        }

        if(user != null){

            try{
                manAdmin.getDetailsOfUser().setGender(Gender.valueOf(request.getGender()));
            }catch (IllegalArgumentException exception){
                throw new InvalidValueException("Please, enter valid gender like MALE, FEMALE.");
            }

            manAdmin.getDetailsOfUser().setGsm(request.getGsm());
            manAdmin.getDetailsOfUser().setLastName(request.getLastName());
            manAdmin.getDetailsOfUser().setFirstName(request.getFirstName());

            try {
                manAdmin.getDetailsOfUser().setBirthDate(LocalDate.parse(request.getBirthDate()));
            }catch (DateTimeParseException exception){
                throw new InvalidValueException("Please, enter valid date in this format yyyy-mm-dd.");
            }

            try {
                detailsOfUserRepository.save(manAdmin.getDetailsOfUser());
                return isAdmin? "The details of the admin was updated successfully."
                        : "The details of the manager was updated successfully.";
            }catch (RuntimeException exception){
                throw new UserForbiddenValuesException("Please enter valid values");
            }

        }else{
            throw isAdmin? new UserNotFoundException("There is no such an admin.")
                    : new UserNotFoundException("There is no such an manager.");
        }
    }

    public String deleteManagerAdmin(Long id, CustomerDeleteRequest request){

        ManagerAdmin managerAdmin = managerAdminRepository.findById(id).orElseThrow(()->new UserNotFoundException("There is no such an user."));

        String role = "";

        if(managerAdmin.getUser().getRole().equals(Role.ADMIN)){
            role = "admin";
        }else if(managerAdmin.getUser().getRole().equals(Role.MANAGER)){
            role = "manager";
        }

        User user = userRepository.findById(managerAdmin.getUser().getId()).orElse(null);

        if(!request.getPassword().equals(request.getVerifyPassword())){
            throw new InvalidPasswordException("Your verified password does not match with your new password.");
        }else if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new IncorrectPasswordException("Incorrect password.");
        }else{
            String email = user.getEmail();
            managerAdminRepository.deleteById(managerAdmin.getId());
            return "The "+role+" whose email is "+email+" was removed from the system.";
        }
    }
}