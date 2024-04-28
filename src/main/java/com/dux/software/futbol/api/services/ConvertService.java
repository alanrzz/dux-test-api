package com.dux.software.futbol.api.services;

import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.entities.Team;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConvertService {

  private final ModelMapper mapper = new ModelMapper();

  public Team convertToEntity(TeamDto teamDto) {
    if (teamDto == null) {
      throw new NullPointerException( "TeamDto no debe ser nulo al convertir a entidad" );
    }
    return mapper.map( teamDto, Team.class );
  }

  public TeamDto convertToDto(Team team) {
    if (team == null)
      throw new NullPointerException( "Team no debe ser nulo al convertir a dto" );
    return mapper.map( team, TeamDto.class );
  }

}
