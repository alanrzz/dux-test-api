package com.dux.software.futbol.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info( metadata() );
  }

  public Info metadata() {
    return new Info()
        .title( "Futbol API" )
        .description( "API para gestionar información de equipos de fútbol." )
        .version( "1.0" )
        .license( apiLicence() )
        .contact( apiContact() );
  }

  private License apiLicence() {
    return new License()
        .name( "Apache 2.0" )
        .url( "http://springdoc.org" );
  }

  private Contact apiContact() {
    return new Contact()
        .name( "Alan Rodriguez" )
        .url( "https://github.com/alanrzz" )
        .email( "alanrodriguezalexandro@gmail.com" );
  }

}