package com.dux.software.futbol.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

  private Long id;
  private String nombre;
  private String liga;
  private String pais;

}
