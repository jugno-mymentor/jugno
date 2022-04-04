package com.jawad.myspringauth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public String displayHomePage() {
    //todo this is the default page to display (index page) when user first lands to our website
    return "</H1>DisplayHomePage</H1>";
  }

}
