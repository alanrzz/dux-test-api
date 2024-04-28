package com.dux.software.futbol.api.controllers.impl;

import com.dux.software.futbol.api.controllers.AuthController;
import com.dux.software.futbol.api.dto.AuthResponseDto;
import com.dux.software.futbol.api.dto.LoginRequestDto;
import com.dux.software.futbol.api.dto.RegisterRequestDto;
import com.dux.software.futbol.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

  private final AuthService authService;

  @Override
  @PostMapping(value = "/login")
  public AuthResponseDto login(LoginRequestDto loginRequestDto) {
    return authService.login( loginRequestDto );
  }

  @Override
  @PostMapping(value = "/register")
  public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
    return authService.register( registerRequestDto );
  }

}
