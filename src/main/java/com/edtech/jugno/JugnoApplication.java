package com.edtech.jugno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

//TODO-Properties: Profiles/configuation properties/externalized configuration:
//TODO-Properties: if we use
@SpringBootApplication
//@PropertySources({
//        @PropertySource("classpath:rdbms.${.properties"),
//        @PropertySource("classpath:bar.properties")
//})
@ConstructorBinding
@ConfigurationProperties("my.service")
public class JugnoApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(JugnoApplication.class);
  public JugnoApplication() {
  }

  public static void main(String[] args) {
    SpringApplication.run(com.edtech.jugno.JugnoApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  // See: https://stacktraceguru.com/springboot/run-method-on-startup
  public void runAfterStartup() {
    LOGGER.info("logging is ready!");
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
