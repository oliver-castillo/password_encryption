package com.project.password_encryption.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.password_encryption.services.IPasswordEncryptionService;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/encrypt_password")
public class PasswordEncryptionREST {

  private final IPasswordEncryptionService passwordEncryptionService;

  public PasswordEncryptionREST(IPasswordEncryptionService passwordEncryptionService) {
    this.passwordEncryptionService = passwordEncryptionService;
  }

  private Map<String, String> response = new HashMap<>();
  private String hashedPassword = "";

  @GetMapping("/bcrypt")
  public ResponseEntity<Object> useBcrypt(@RequestBody String password) {
    hashedPassword = passwordEncryptionService.encodeWithBcrypt(password);
    response.put("hashedPassword", hashedPassword);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/scrypt")
  public ResponseEntity<Object> useScrypt(@RequestBody String password) {
    hashedPassword = passwordEncryptionService.encodeWithScrypt(password);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
  }

  @GetMapping("/pbkdf2")
  public ResponseEntity<Object> usePbkdf2(@RequestBody String password) {
    hashedPassword = passwordEncryptionService.encodeWithPbkdf2(password);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
  }

  @GetMapping("/argon2id")
  public ResponseEntity<Object> useArgon2id(@RequestBody Map<String, Object> params) {
    hashedPassword = passwordEncryptionService.encodeWithArgon2id(params);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
  }

}
