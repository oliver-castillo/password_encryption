package com.project.password_encryption.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class APIController {

  @GetMapping("/")
  public String showIndex() {
    return "index";
  }
}
