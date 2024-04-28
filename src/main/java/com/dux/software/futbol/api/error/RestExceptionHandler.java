package com.dux.software.futbol.api.error;

import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.exceptions.UserNameAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> AllUnhandledExceptions(Exception ex) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setMensaje(ex.getMessage());
    return this.buildResponse(apiError);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<Object> ResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
    apiError.setMensaje(resourceNotFoundException.getMessage());
    return this.buildResponse(apiError);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> DataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMensaje(dataIntegrityViolationException.getMessage());
    return buildResponse(apiError);
  }

  @ExceptionHandler(UserNameAlreadyExistsException.class)
  protected ResponseEntity<Object> UserNameAlreadyExistsException(UserNameAlreadyExistsException userNameAlreadyExistsException) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMensaje(userNameAlreadyExistsException.getMessage());
    return buildResponse(apiError);
  }

  private ResponseEntity<Object> buildResponse(ApiError apiError) {
    return ResponseEntity.status(apiError.getCodigo()).body(apiError);
  }

}