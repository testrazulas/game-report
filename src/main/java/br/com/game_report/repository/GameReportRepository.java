package br.com.game_report.repository;

import br.com.game_report.domain.GameReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameReportRepository extends JpaRepository<GameReport, Long> {}
