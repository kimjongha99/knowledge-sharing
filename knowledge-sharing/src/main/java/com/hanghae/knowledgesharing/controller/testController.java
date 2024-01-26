package com.hanghae.knowledgesharing.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/test")
public class testController {


    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Convert authorities to a string
            String authorities = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(", "));

            String email = authentication.getName(); // Assuming the username is the email
            return ResponseEntity.ok("Member Service is Healthy, Authenticated user: " + email + ", Authorities: " + authorities);
        }

        return ResponseEntity.ok("Authentication is required.");
    }

}
