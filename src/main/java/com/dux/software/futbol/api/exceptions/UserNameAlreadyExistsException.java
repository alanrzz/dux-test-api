package com.dux.software.futbol.api.exceptions;

import java.io.Serial;

public class UserNameAlreadyExistsException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 854598681642203870L;

  public UserNameAlreadyExistsException(String message) {
    super(message);
  }

}