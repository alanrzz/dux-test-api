package com.dux.software.futbol.api.services;

import static java.util.stream.Collectors.toList;

import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.entities.Team;
import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.repositories.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final ConvertService convertService;

  public List<TeamDto> findAll() {
    return teamRepository.findAll().parallelStream()
        .map( convertService::convertToDto ).collect( toList() );
  }

  public TeamDto findById(Long id) {
    return convertService.convertToDto( findOrFail( id ) );
  }

  public List<TeamDto> findByName(String name) {
    return teamRepository.findAllByNombreContaining( name ).parallelStream()
        .map( convertService::convertToDto ).collect( toList() );
  }

  public TeamDto create(TeamDto teamDto) {
    try {
      Team team = teamRepository.save( convertService.convertToEntity( teamDto ) );
      return convertService.convertToDto( team );
    } catch (DataIntegrityViolationException ex) {
      throw new DataIntegrityViolationException( "La solicitud es inválida" );
    }
  }

  public TeamDto edit(Long id, TeamDto teamDto) {
    findOrFail( id );
    Team team = this.convertService.convertToEntity( teamDto );
    team.setId( id );
    return this.convertService.convertToDto( teamRepository.save( team ) );
  }

  public void delete(Long id) {
    teamRepository.delete( findOrFail(id) );
  }

  private Team findOrFail(Long id) {
    return teamRepository.findById( id ).orElseThrow(() -> new ResourceNotFoundException( "Equipo no encontrado" ));
  }

}
