package com.dux.software.futbol.api.controllers.impl;

import com.dux.software.futbol.api.controllers.TeamController;
import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.services.TeamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
@RequiredArgsConstructor
public class TeamControllerImpl implements TeamController {

  private final TeamService teamService;

  @Override
  @GetMapping
  public List<TeamDto> findAll() {
    return this.teamService.findAll();
  }

  @Override
  @GetMapping(value = "{id}")
  public TeamDto findById(Long id) {
    return this.teamService.findById( id );
  }

  @Override
  @GetMapping(value = "/buscar")
  public List<TeamDto> findByName(String name) {
    return this.teamService.findByName( name );
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TeamDto create(TeamDto teamDto) {
    return this.teamService.create( teamDto );
  }

  @Override
  @PutMapping(value= "{id}")
  public TeamDto edit(Long id, TeamDto teamDto) {
    return this.teamService.edit( id, teamDto );
  }

  @Override
  @DeleteMapping(value= "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(Long id) {
    this.teamService.delete( id );
  }

}
