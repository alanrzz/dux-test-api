package com.dux.software.futbol.api.services;

import static java.util.stream.Collectors.toList;

import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.entities.Team;
import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.repositories.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

  private final TeamRepository teamRepository;
  private final ConvertService convertService;

  public List<TeamDto> findAll() {
    log.debug("START findAll()");
    return teamRepository.findAll().parallelStream()
        .map( convertService::convertToDto ).collect( toList() );
  }

  public TeamDto findById(Long id) {
    log.debug("START findById({})", id);
    return convertService.convertToDto( findOrFail( id ) );
  }

  public List<TeamDto> findByName(String name) {
    log.debug("START findByName({})", name);
    return teamRepository.findAllByNombreContaining( name ).parallelStream()
        .map( convertService::convertToDto ).collect( toList() );
  }

  public TeamDto create(TeamDto teamDto) {
    log.debug("START create({})", teamDto.getId());
    try {
      Team team = teamRepository.save( convertService.convertToEntity( teamDto ) );
      return convertService.convertToDto( team );
    } catch (DataIntegrityViolationException ex) {
      throw new DataIntegrityViolationException( "La solicitud es inválida" );
    }
  }

  public TeamDto edit(Long id, TeamDto teamDto) {
    log.debug("START edit({},{})", id, teamDto.getNombre());
    findOrFail( id );
    Team team = this.convertService.convertToEntity( teamDto );
    team.setId( id );
    return this.convertService.convertToDto( teamRepository.save( team ) );
  }

  public void delete(Long id) {
    log.debug("START delete({})", id);
    teamRepository.delete( findOrFail(id) );
  }

  private Team findOrFail(Long id) {
    log.debug("START findOrFail({})", id);
    return teamRepository.findById( id ).orElseThrow(() -> new ResourceNotFoundException( "Equipo no encontrado" ));
  }

}
