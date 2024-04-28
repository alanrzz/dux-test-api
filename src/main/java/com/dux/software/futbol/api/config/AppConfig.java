package com.dux.software.futbol.api.config;

import com.dux.software.futbol.api.entities.User;
import com.dux.software.futbol.api.enums.Role;
import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Value("${spring.jwt.defaultUser.username}")
  private String username;
  @Value("${spring.jwt.defaultUser.password}")
  private String password;
  @Value("${spring.jwt.defaultUser.role}")
  private String role;


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService( userDetailsService() );
    daoAuthenticationProvider.setPasswordEncoder( passwordEncoder() );
    return daoAuthenticationProvider;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return this::findOrFail;
  }

  @PostConstruct
  private void postConstruct() {
    if (userRepository.findByUsername(username).isEmpty()) {
      User defaultUser = User.builder()
          .username( username )
          .password( passwordEncoder.encode( password ) )
          .role( Role.valueOf( role ) )
          .build();
      userRepository.save( defaultUser );
    }
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return passwordEncoder;
  }

  private User findOrFail(String username) {
    return userRepository.findByUsername( username )
        .orElseThrow(() -> new ResourceNotFoundException( "No existe el usuario = " + username ));
  }

}
