package com.jawad.myspringauth.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * Web frontend for all admin functions. In future it will call a REST service for admin functions service other servcie
 */
@RestController
@RequestMapping(value = "/v1/admin/{adminId}")
public class AdminController {

  //mappings for admin functions such as managing users and roles.
  @GetMapping("")
  public String displayAllUsers(@PathVariable("adminId") String adminUserId) {
    return "</H1>Welcome Admin: " + adminUserId + "</H1>";
  }

  //mappings for admin functions such as managing users and roles.
  //todo also specify user id as part of query params
  @GetMapping("/getUser")
  public String displayUserDetails(@RequestParam("userId") String userId) {
    return "</H1>Admin requested to display userId: " + userId + " </H1>";
  }

}
