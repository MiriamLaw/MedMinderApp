package com.medminder.controllers;

import com.medminder.domains.User;
import com.medminder.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller // Use @Controller for Thymeleaf
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired  
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String confirmPassword,
        Model model
    ) {
        logger.info("Received registration request: email={}, password={}, confirmPassword={}", email, password, confirmPassword);
        if (email == null || password == null) {
            model.addAttribute("error", "Email and password are required");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        try {
            userService.registerUser(email, password);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/api/auth/me")
    @ResponseBody
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        String email = authentication.getName();
        return ResponseEntity.ok(java.util.Map.of("email", email));
    }
}
