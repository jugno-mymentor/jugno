package com.edtech.jugno;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

//  @Autowired
  public SecurityConfiguration(DataSource dataSource) {
    super();
    this.dataSource = dataSource;
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
//            .withUser(User.withUsername("user").password("userpass").roles("USER"))
//            .withUser(User.withUsername("admin ").password("adminpass").roles("ADMIN").authorities());
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().anyRequest().hasRole()Authority()
//            .antMatchers("/admin").hasRole("ADMIN")
//            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
////            .antMatchers("/h2-console/**").permitAll()
//            .antMatchers("/**").permitAll()
//            .and().formLogin();
//  }
//
//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web
//            .ignoring()
//            .antMatchers("/h2-console/**");
//  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
