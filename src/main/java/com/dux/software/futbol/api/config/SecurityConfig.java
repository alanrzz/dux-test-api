package com.dux.software.futbol.api.config;

import com.dux.software.futbol.api.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf( AbstractHttpConfigurer::disable )
        .authorizeHttpRequests(authRequest ->
            authRequest.requestMatchers( "/h2-console/**", "/v3/**",
                    "/swagger-ui.html", "/swagger-ui/**", "/auth/**" ).permitAll()
                .anyRequest().authenticated()
        )
        .headers(h -> h.frameOptions( HeadersConfigurer.FrameOptionsConfig::sameOrigin ))
        .sessionManagement( sessionManager ->
            sessionManager.sessionCreationPolicy( SessionCreationPolicy.STATELESS ))
        .authenticationProvider( authenticationProvider )
        .addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}
