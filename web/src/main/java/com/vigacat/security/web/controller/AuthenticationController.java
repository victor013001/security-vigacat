package com.vigacat.security.web.controller;

import com.vigacat.security.service.component.security.TokenService;
import com.vigacat.security.service.component.security.VigacatAuthenticationManager;
import com.vigacat.security.web.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final VigacatAuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            return ResponseEntity.ok(tokenService.createToken(authentication.getName()));
        } catch (BadCredentialsException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "credentials Invalid");
        }
    }

    @GetMapping
    public ResponseEntity<String> getPasswordEncoded(@RequestParam("value") String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }

}
