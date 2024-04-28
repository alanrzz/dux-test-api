package com.dux.software.futbol.api.dto;

import com.dux.software.futbol.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

  private String username;
  private String password;
  private Role role;

}
