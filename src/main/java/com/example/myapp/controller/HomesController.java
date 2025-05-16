package com.example.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomesController {
  @GetMapping("/")
  public String top() {
    return "homes/top";
  }
}
