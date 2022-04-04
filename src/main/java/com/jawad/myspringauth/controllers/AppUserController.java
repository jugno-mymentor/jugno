package com.jawad.myspringauth.controllers;

import com.jawad.myspringauth.model.AppUser;
import com.jawad.myspringauth.services.AppUserManagementService;
import com.jawad.myspringauth.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

//TODO-Controllers: Implement versioning mechanism
@RestController
@RequestMapping(value = "/admin/users",
        //TODO: If the request wants xml response instead of json (accept header is application/xml) then is the
        //TODO: response automatically sent as xml by spring or do we need to do something?
        //-- Specifies what types of content is accepted in the HTTP body.
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        //-- Specifies what types of content a controller mapping can return within the body of the HTTP response.
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AppUserController {

  private final AppUserManagementService appUserManagementService;

  @Autowired
  public AppUserController(AppUserManagementService appUserManagementService) {
    this.appUserManagementService = appUserManagementService;
  }

  //  @PostMapping("/create")
//  public ResponseEntity<AppUser> createAppUser(@Valid @RequestBody AppUser appUser) {
//    appUserManagementService.createAppUser(appUser);
//    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "emailexists", "E-mail already in use")).body(null);
//
//    ResponseEntity<AppUser> responseEntity = ResponseEntity.created(URI.create(String.format("/get/%s",
//            appUser.getUserName()))).body(appUser);
//    return responseEntity;
//  }

  @GetMapping(value = "/getAll")
  @Transactional(readOnly = true)
  public ResponseEntity<Set<AppUser>> getAllUsers() {
    Set<AppUser> appUsers = appUserManagementService.getAllAppUsers();
    return ResponseEntity.ok(appUsers);
  }


  /**
   *
   * @param appUserId
   * @param appUserDetails
   * @return
   * @throws ResourceNotFoundException
   * @See See https://www.programcreek.com/java-api-examples/?class=org.springframework.http.MediaType&method=APPLICATION_JSON_VALUE
   */
//  @PutMapping("/update/{id}")
//  public ResponseEntity<AppUser> updateAppUser(@PathVariable(value = "id") Integer appUserId,
//                                               @Valid @RequestBody AppUser appUserDetails)
//          throws ResourceNotFoundException {
//    appUserManagementService.updateAppUser(appUserId, appUserDetails);
//    //todo-jpa: there was an example where response had details abot the created or updated user in response.
//    return ResponseEntity.ok(updatedEmployee);
//  }
//
//  @DeleteMapping("/employees/{id}")
//  public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
//          throws ResourceNotFoundException {
//    Employee employee = employeeRepository.findById(employeeId)
//            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//    employeeRepository.delete(employee);
//    Map<String, Boolean> response = new HashMap<>();
//    response.put("deleted", Boolean.TRUE);
//    return response;
//  }
//
//  //  @ResponseBody
////  In classic Spring MVC applications, endpoints usually return rendered HTML pages. Sometimes we only need to return the actual data; for example, when we use the endpoint with AJAX.
////
////  In such cases, we can mark the request handler method with @ResponseBody, and Spring treats the result value of the method as the HTTP response body itself.
////
//  @GetMapping("")
//  public ResponseEntity<String> displayUserHomePage(@PathVariable("userId") String userId) {
////    return "</H1>Welcome " + userId + "</H1>";
//    return ResponseEntity.ok().header("my-header-key", "my header value").body("reposnse body");
//  }

}
