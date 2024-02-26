package com.onlineFoodOrdering.onlineFoodOrdering.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.User;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.EmailAlreadyExistsException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.IncorrectPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.DetailsOfUserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.UserRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.security.config.JwtService;
import com.onlineFoodOrdering.onlineFoodOrdering.security.token.Token;
import com.onlineFoodOrdering.onlineFoodOrdering.security.token.TokenRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.security.token.TokenType;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final DetailsOfUserRepository detailsOfUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final ManagerAdminService managerAdminService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    boolean isOK = false;
    public String register(RegisterRequest request){

        User userException = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(userException != null){
            throw new EmailAlreadyExistsException("The email has already exists.");
        }

        if(request.getRole().equals(Role.CUSTOMER)){
            customerService.addACustomer(request);
            isOK = true;
        }else if(request.getRole().equals(Role.OWNER)){
            ownerService.addOneOwner(request);
            isOK = true;
        }else if(request.getRole().equals(Role.ADMIN) || request.getRole().equals(Role.MANAGER)){
            managerAdminService.addOneManagerAdmin(request);
            isOK = true;
        }

        if(isOK){

            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user,jwtToken);

            String result = "access token: "+jwtToken+"\n";
            result+="refresh token: "+refreshToken;

            return result;
        }else{
            return "There is an error about entered information";
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }catch (AuthenticationException exception){
            throw new IncorrectPasswordException("The password you entered is incorrect for this email.");
        }

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if(validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail != null){
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();

            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user,accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .refreshToken(refreshToken)
                        .accessToken(accessToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
