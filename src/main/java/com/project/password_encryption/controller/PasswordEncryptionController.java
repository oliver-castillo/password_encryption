package com.project.password_encryption.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.password_encryption.services.PasswordEncoderService;

@Controller
@RequestMapping("/")
public class PasswordEncryptionController {

  PasswordEncoderService pswEncoderService = new PasswordEncoderService();

  @GetMapping("/{password}")
  public ResponseEntity<Object> index(@PathVariable String password) {
    return new ResponseEntity<>(pswEncoderService.encodeWithSHA2(password), HttpStatus.OK);
  }

}
