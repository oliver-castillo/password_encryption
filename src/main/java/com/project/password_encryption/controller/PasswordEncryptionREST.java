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
public class PasswordEncryptionREST {

  IPasswordEncryptionService passwordEncryptionService;

  public PasswordEncryptionREST(IPasswordEncryptionService passwordEncryptionService) {
    this.passwordEncryptionService = passwordEncryptionService;
  }

  private String hashedPassword = "";

  @GetMapping("/bcrypt")
  public ResponseEntity<Object> useBcrypt(@RequestBody String password) {
    hashedPassword = passwordEncryptionService.encodeWithBcrypt(password);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
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

  @GetMapping("/{algorithm}")
  public ResponseEntity<Object> useAlgorithm(@RequestBody String password, @PathVariable String algorithm) {
    hashedPassword = passwordEncryptionService.encodeWithAlgorithm(password, algorithm);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
  }

  @GetMapping("/argon2id")
  public ResponseEntity<Object> useArgon2id(@RequestBody String password, @RequestBody Map<String, Integer> params) {
    hashedPassword = passwordEncryptionService.encodeWithArgon2id(password, params);
    return new ResponseEntity<>(hashedPassword, HttpStatus.OK);
  }

}
