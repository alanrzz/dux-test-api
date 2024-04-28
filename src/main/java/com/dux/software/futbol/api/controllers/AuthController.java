package com.dux.software.futbol.api.controllers;

import com.dux.software.futbol.api.dto.AuthResponseDto;
import com.dux.software.futbol.api.dto.LoginRequestDto;
import com.dux.software.futbol.api.dto.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Operaciones relacionadas con la autenticación.")
public interface AuthController {

  @Operation(summary = "Autenticar un usuario.")
  AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception;

  @Operation(summary = "Registrar un usuario.")
  AuthResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws Exception;

}
