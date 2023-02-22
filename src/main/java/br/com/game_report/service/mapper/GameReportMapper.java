package br.com.game_report.service.mapper;

import br.com.game_report.domain.GameReport;
import br.com.game_report.service.dto.GameReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameReport} and its DTO {@link GameReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameReportMapper extends EntityMapper<GameReportDTO, GameReport> {}
