package com.organization.crm.modules.Auth;

import com.organization.crm.dtos.AuthResponse;
import com.organization.crm.dtos.LoginReq;
import com.organization.crm.dtos.SignupReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServices authServices;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginReq body){
        AuthResponse resp = authServices.login(body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @PostMapping("signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupReq body){
        AuthResponse resp = authServices.signup(body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }
}
