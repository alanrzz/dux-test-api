package com.dux.software.futbol.api.controllers;

import com.dux.software.futbol.api.dto.TeamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Teams", description = "Operaciones relacionadas con los equipos de fútbol.")
public interface TeamController {

  @Operation(summary = "Consulta de todos los equipos.")
  @SecurityRequirement(name = "Authorization")
  List<TeamDto> findAll() throws Exception;

  @Operation(summary = "Consulta de un equipo por id.")
  @SecurityRequirement(name = "Authorization")
  TeamDto findById(@PathVariable("id") Long id) throws Exception;

  @Operation(summary = "Búsqueda de equipos por nombre.")
  @SecurityRequirement(name = "Authorization")
  List<TeamDto> findByName(@RequestParam("nombre") String name) throws Exception;

  @Operation(summary = "Creación de un equipo.")
  @SecurityRequirement(name = "Authorization")
  TeamDto create(@Valid @RequestBody TeamDto teamDto) throws Exception;

  @Operation(summary = "Actualización de información de un equipo.")
  @SecurityRequirement(name = "Authorization")
  TeamDto edit(@PathVariable("id") Long id, @Valid @RequestBody TeamDto teamDto) throws Exception;

  @Operation(summary = "Eliminación de un equipo.")
  @SecurityRequirement(name = "Authorization")
  void delete(@PathVariable("id") Long id) throws Exception;

}
