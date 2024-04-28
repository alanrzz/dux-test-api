package com.dux.software.futbol.api.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6959970154230391611L;

  public ResourceNotFoundException(String message) {
    super(message);
  }

}