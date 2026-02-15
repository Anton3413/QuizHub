package com.anton3413.quiz_hub.controller;

import com.anton3413.quiz_hub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private VerificationTokenService tokenService;


    @PostMapping


    @GetMapping("/confirm")
    public ResponseEntity activateAccountWithToken(@RequestParam String token){
        boolean result = tokenService.verifyToken(token);

        if (result) {
            return ResponseEntity.ok("Аккаунт успешно активирован!");
        } else {
            return ResponseEntity.badRequest().body("Токен недействителен или просрочен.");
        }
    }

}