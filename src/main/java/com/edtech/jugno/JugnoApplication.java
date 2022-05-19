package com.edtech.jugno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
//@PropertySources({
//        @PropertySource("classpath:rdbms.${.properties"),
//        @PropertySource("classpath:bar.properties")
//})
public class JugnoApplication {

  public JugnoApplication() {
  }

  public static void main(String[] args) {
    SpringApplication.run(com.edtech.jugno.JugnoApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  // See: https://stacktraceguru.com/springboot/run-method-on-startup
  public void runAfterStartup() {
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
