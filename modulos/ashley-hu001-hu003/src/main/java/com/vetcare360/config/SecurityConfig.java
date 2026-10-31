package com.vetcare360.config;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
  return http.authorizeHttpRequests(a->a
    .requestMatchers("/registro","/login","/css/**","/error").permitAll()
    .anyRequest().authenticated())
   .formLogin(f->f.loginPage("/login").defaultSuccessUrl("/inicio",true).permitAll())
   .logout(l->l.logoutSuccessUrl("/login?logout").permitAll())
   .build();
 }
}