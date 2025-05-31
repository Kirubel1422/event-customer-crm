package com.organization.crm.modules.Auth;

import com.organization.crm.dtos.AuthResponse;
import com.organization.crm.dtos.LoginReq;
import com.organization.crm.dtos.SignupReq;
import com.organization.crm.modules.Users.UserRepo;
import com.organization.crm.modules.Users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServices {
    private final JWTService jwtService;
    private final UserRepo userRepo;

    public AuthResponse signup(SignupReq body) throws BadCredentialsException {
        // Check if the user already exists
        Optional<Users> preUser = userRepo.findByEmail(body.email());

        if(preUser.isPresent()){
            throw new BadCredentialsException("Email already in use");
        }

        Users user = new Users();
        user.setEmail(body.email());
        user.setPassword(encodePassword(body.password()));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        user.setRoles(roles);

        Users savedUser = userRepo.save(user);
        String token = jwtService.generateToken(savedUser.getId());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRoles(),
                token
        );
    }

    public AuthResponse login(LoginReq login) throws BadCredentialsException {
        Optional<Users> user = userRepo.findByEmail(login.email());

        if(!user.isPresent()){
            throw new BadCredentialsException("Email or password is incorrect");
        }

        Users savedUser = user.get();
        String hashedPassword = savedUser.getPassword();
        String passwordFromReq = login.password();

        if(!isCorrectPassword(passwordFromReq, hashedPassword)){
            throw new BadCredentialsException("Email or password is incorrect");
        }

        String token = jwtService.generateToken(savedUser.getId());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRoles(),
                token
        );
    }

    private String encodePassword(String password){
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        return bcrypt.encode(password);
    }

    private boolean isCorrectPassword(String password, String hashedPassword){
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(password, hashedPassword);
    }
}
