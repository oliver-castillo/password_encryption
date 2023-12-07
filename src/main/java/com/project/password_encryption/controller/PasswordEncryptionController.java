package com.project.password_encryption.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.password_encryption.services.PasswordEncryptionService;

@Controller
public class PasswordEncryptionController {

  PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();

  @GetMapping("/{password}")
  public ModelAndView index(@PathVariable String password) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("title", passwordEncryptionService.encodeWithBcrypt("password"));
    modelAndView.setViewName("index");
    return modelAndView;
  }

}
