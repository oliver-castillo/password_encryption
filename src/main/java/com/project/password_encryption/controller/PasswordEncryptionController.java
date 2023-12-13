package com.project.password_encryption.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.password_encryption.services.IPasswordEncryptionService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/encrypt_password")
public class PasswordEncryptionController {

    private final IPasswordEncryptionService passwordEncryptionService;

    public PasswordEncryptionController(IPasswordEncryptionService passwordEncryptionService) {
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @GetMapping("/{algorithm}")
    public ResponseEntity<Object> getEncryptedPassword(
            @PathVariable String algorithm,
            @RequestBody Map<String, Object> params) {
        Map<String, String> response = passwordEncryptionService.encodePassword(algorithm, params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
