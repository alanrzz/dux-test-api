package com.dux.software.futbol.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dux.software.futbol.api.dto.TeamDto;
import com.dux.software.futbol.api.entities.Team;
import com.dux.software.futbol.api.exceptions.ResourceNotFoundException;
import com.dux.software.futbol.api.repositories.TeamRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TeamServiceAssertTest {

  @InjectMocks
  private TeamService teamService;

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private ConvertService convertService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks( this );
  }

  @Test
  public void should_find_all_teams() {
    TeamDto teamDto = new TeamDto( 1L, "Atlético Madrid", "La Liga", "España" );
    List<Team> teams = List.of( new Team( 1L, "Atlético Madrid", "La Liga", "España" ) );

    when( teamRepository.findAll() ).thenReturn( teams );
    when( convertService.convertToDto( any(Team.class) ) ).thenReturn( teamDto );

    List<TeamDto> responseTeamDtoList = teamService.findAll();

    assertEquals( teams.size(), responseTeamDtoList.size(), "SIZE de List<Team> no es igual a SIZE de List<TeamDto>" );

    verify( teamRepository, times( 1 ) ).findAll();
  }

  @Test
  public void should_find_team_by_id() {
    Long teamId = 1L;
    Team team = new Team( 1L, "Atlético Madrid", "La Liga", "España" );
    TeamDto teamDto = new TeamDto( 1L, "Atlético Madrid", "La Liga", "España" );

    when( teamRepository.findById( teamId ) ).thenReturn( Optional.of( team ) );
    when( convertService.convertToDto( any(Team.class) ) ).thenReturn( teamDto );

    TeamDto responseTeamDto = teamService.findById( teamId );

    assertEquals( teamDto.getId(), responseTeamDto.getId(), "ID de TeamDto no es igual a ID de ResponseTeamDto" );
    assertEquals( teamDto.getNombre(), responseTeamDto.getNombre(), "ID de TeamDto no es igual a NOMBRE de ResponseTeamDto" );
    assertEquals( teamDto.getLiga(), responseTeamDto.getLiga(), "ID de TeamDto no es igual a LIGA de ResponseTeamDto" );
    assertEquals( teamDto.getPais(), responseTeamDto.getPais(), "ID de TeamDto no es igual a PAIS de ResponseTeamDto" );

    verify( teamRepository, times( 1 ) ).findById( teamId );
  }

  @Test
  public void should_find_team_by_name() {
    String teamName = "Atlético Madrid";
    TeamDto teamDto = new TeamDto( 1L, "Atlético Madrid", "La Liga", "España" );
    List<Team> teams = List.of( new Team( 1L, "Atlético Madrid", "La Liga", "España" ) );

    when( teamRepository.findAllByNombreContaining( teamName ) ).thenReturn( teams );
    when( convertService.convertToDto( any(Team.class) ) ).thenReturn( teamDto );

    List<TeamDto> responseTeamDtoList = teamService.findByName( teamName );

    assertEquals(teams.size(), responseTeamDtoList.size(), "SIZE de List<Team> no es igual a SIZE de List<TeamDto>");
    responseTeamDtoList.forEach( dto ->
        assertEquals( teamName, dto.getNombre(), "NOMBRE de Team no es igual a NOMBRE de TeamDto" ) );

    verify( teamRepository, times( 1 ) ).findAllByNombreContaining( teamName );
  }

    @Test
    public void should_successfully_create_a_team() {
      TeamDto teamDto = new TeamDto( 1L, "Atlético Madrid", "La Liga", "España" );
      Team team = new Team( 1L, "Atlético Madrid", "La Liga", "España" );
      Team savedTeam = new Team( 1L, "Atlético Madrid", "La Liga", "España" );

      when( convertService.convertToEntity( teamDto ) ).thenReturn( team );
      when( teamRepository.save( team ) ).thenReturn( savedTeam );
      when( convertService.convertToDto( savedTeam ) ).thenReturn( teamDto );

      TeamDto responseTeamDto = teamService.create( teamDto );

      assertEquals( teamDto.getId(), responseTeamDto.getId(), "ID de TeamDto no es igual a ID de ResponseTeamDto" );
      assertEquals( teamDto.getNombre(), responseTeamDto.getNombre(), "ID de TeamDto no es igual a NOMBRE de ResponseTeamDto" );
      assertEquals( teamDto.getLiga(), responseTeamDto.getLiga(), "ID de TeamDto no es igual a LIGA de ResponseTeamDto" );
      assertEquals( teamDto.getPais(), responseTeamDto.getPais(), "ID de TeamDto no es igual a PAIS de ResponseTeamDto" );

      verify( convertService, times( 1 ) ).convertToEntity( teamDto );
      verify( teamRepository, times( 1 ) ).save( team );
      verify( convertService, times( 1 ) ).convertToDto( savedTeam );
    }

  @Test
  public void should_successfully_edit_a_team() {
    Long teamId = 1L;
    TeamDto updatedTeamDto = new TeamDto( teamId, "Nuevo nombre", "Nueva liga", "Nuevo pais" );
    Team existingTeam = new Team( teamId, "Atlético Madrid", "La Liga", "España" );
    Team updatedTeam = new Team( teamId, "Nuevo nombre", "Nueva liga", "Nuevo pais" );

    when( teamRepository.findById( teamId) ).thenReturn( Optional.of( existingTeam ) );
    when( convertService.convertToEntity( updatedTeamDto) ).thenReturn( updatedTeam );
    when( teamRepository.save( updatedTeam ) ).thenReturn( updatedTeam );
    when( convertService.convertToDto( updatedTeam ) ).thenReturn( updatedTeamDto );

    TeamDto responseTeamDto = teamService.edit( teamId, updatedTeamDto );

    assertEquals( updatedTeamDto.getId(), responseTeamDto.getId(), "ID de UpdatedTeamDto no es igual a ID de ResponseTeamDto" );
    assertEquals( updatedTeamDto.getNombre(), responseTeamDto.getNombre(), "ID de UpdatedTeamDto no es igual a NOMBRE de ResponseTeamDto" );
    assertEquals( updatedTeamDto.getLiga(), responseTeamDto.getLiga(), "ID de UpdatedTeamDto no es igual a LIGA de ResponseTeamDto" );
    assertEquals( updatedTeamDto.getPais(), responseTeamDto.getPais(), "ID de UpdatedTeamDto no es igual a PAIS de ResponseTeamDto" );

    verify( teamRepository, times(1)).findById( teamId );
    verify( convertService, times(1)).convertToEntity( updatedTeamDto );
    verify( teamRepository, times(1)).save( updatedTeam );
    verify( convertService, times(1)).convertToDto( updatedTeam );
  }

  @Test
  public void should_successfully_delete_existing_team() {
    Long teamId = 1L;
    Team existingTeam = new Team( teamId, "Atlético Madrid", "La Liga", "España" );

    when( teamRepository.findById(teamId) ).thenReturn( Optional.of( existingTeam ) );

    teamService.delete(teamId);

    verify( teamRepository, times(1) ).findById( teamId );
    verify( teamRepository, times(1) ).delete( existingTeam );
  }

  @Test
  public void should_throw_exception_when_deleting_non_existing_team() {
    Long teamId = 1L;

    when( teamRepository.findById( teamId ) ).thenReturn( Optional.empty() );

    var exp = assertThrows( ResourceNotFoundException.class, ()
        -> teamService.delete( teamId ) );
    assertEquals( "Equipo no encontrado", exp.getMessage(),
        "MENSAJE de ResourceNotFoundException no es igual al esperado");

    verify( teamRepository, times(1) ).findById( teamId );
  }

}