package com.dux.software.futbol.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.entities.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertServiceAssertTest {

  private ConvertService convertService;

  @BeforeEach
  void setUp() {
    convertService = new ConvertService();
  }

  @Test
  public void should_convert_teamDto_to_team() {
    TeamDto teamDto = new TeamDto( 1L, "Atlético Madrid", "La Liga", "España" );

    Team team = convertService.convertToEntity( teamDto );

    assertEquals( teamDto.getId(), team.getId(), "ID de Team no es igual a ID de TeamDto" );
    assertEquals( teamDto.getNombre(), team.getNombre(), "NOMBRE de Team no es igual a NOMBRE de TeamDto");
    assertEquals( teamDto.getLiga(), team.getLiga(), "LIGA de Team no es igual a LIGA de TeamDto");
    assertEquals( teamDto.getPais(), team.getPais(), "PAIS de Team no es igual a PAIS de TeamDto" );
  }

  @Test
  public void should_throw_null_pointer_exception_when_teamDto_is_null() {
    var exp = assertThrows( NullPointerException.class, ()
        -> convertService.convertToEntity( null ) );
    assertEquals( "TeamDto no debe ser nulo al convertir a entidad", exp.getMessage(),
        "MENSAJE de NullPointerException no es igual al esperado");
  }

  @Test
  public void should_convert_team_to_teamDto() {
    Team team = new Team( 1L, "Juventus FC", "Serie A", "Italia" );

    TeamDto teamDto = convertService.convertToDto( team );

    assertEquals( teamDto.getId(), team.getId(), "ID de TeamDto no es igual a ID de Team" );
    assertEquals( teamDto.getNombre(), team.getNombre(), "NOMBRE de TeamDto no es igual a NOMBRE de Team");
    assertEquals( teamDto.getLiga(), team.getLiga(), "LIGA de TeamDto no es igual a LIGA de Team");
    assertEquals( teamDto.getPais(), team.getPais(), "PAIS de TeamDto no es igual a PAIS de Team" );
  }

  @Test
  public void should_throw_null_pointer_exception_when_team_is_null() {
    var exp = assertThrows( NullPointerException.class, ()
        -> convertService.convertToDto( null ) );
    assertEquals( "Team no debe ser nulo al convertir a dto", exp.getMessage(),
        "MENSAJE de NullPointerException no es igual al esperado" );
  }

}



