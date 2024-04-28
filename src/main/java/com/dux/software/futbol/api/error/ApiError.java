package com.dux.software.futbol.api.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

  private String mensaje;
  private int codigo;

  public ApiError(HttpStatus codigo) {
    this.codigo = codigo.value();
  }

}