package com.dux.software.futbol.api.repositories;

import com.dux.software.futbol.api.entities.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

  List<Team> findAllByNombreContaining(String name);

}
