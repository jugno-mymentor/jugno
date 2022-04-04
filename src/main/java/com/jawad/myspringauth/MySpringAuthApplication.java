package com.jawad.myspringauth;

import com.jawad.myspringauth.model.AppAuthority;
import com.jawad.myspringauth.model.AppUser;
import com.jawad.myspringauth.services.AppAuthorityManagementService;
import com.jawad.myspringauth.services.AppUserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Optional;

@SpringBootApplication
//@PropertySources({
//        @PropertySource("classpath:rdbms.${.properties"),
//        @PropertySource("classpath:bar.properties")
//})
public class MySpringAuthApplication {

  private final AppUserManagementService appUserManagementService;
  private final AppAuthorityManagementService appAuthorityManagementService;

  @Autowired
  public MySpringAuthApplication(AppUserManagementService appUserManagementService,
                                 AppAuthorityManagementService appAuthorityManagementService) {
    this.appUserManagementService = appUserManagementService;
    this.appAuthorityManagementService = appAuthorityManagementService;
  }

  public static void main(String[] args) {
    SpringApplication.run(MySpringAuthApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  // See: https://stacktraceguru.com/springboot/run-method-on-startup
  public void runAfterStartup() {
//    AppAuthority appAuthority = new AppAuthority();
//    appAuthority.setAuthorityName("auth1");
//    appAuthorityManagementService.createAppAuthority(appAuthority);

    Optional<AppAuthority> optionalAppAuthority = appAuthorityManagementService.getAuthorityByName("auth1");
    Optional<AppUser> optionalAppUser = appUserManagementService.getByUserName("jawad1");
    AppUser appUser = optionalAppUser.get();
    appUser.addAppAuthority(optionalAppAuthority.get());
//    AppUser appUser = new AppUser();
//    appUser.setUserName("jawad3");
//    appUser.setPassword("jawad3");
//    appUser.setPrimaryEmailAddress("jawad3");
//    appUser.addAppAuthority(appAuthority);
    appUserManagementService.update(appUser);

    System.out.println("Yaaah, I am running........");
  }

//  @Bean
//  public PhysicalNamingStrategy getPropertyNameBoundNamingStrategy() {
//    return new CamelCaseToUnderscoresNamingStrategy() {
//
//      @Override
//      public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
//        return new Identifier(identifier.getText(), true);
//      }
//
//    };
//  }

}
