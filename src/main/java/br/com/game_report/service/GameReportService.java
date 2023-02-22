package br.com.game_report.service;

import br.com.game_report.domain.GameReport;
import br.com.game_report.repository.GameReportRepository;
import br.com.game_report.service.dto.GameReportDTO;
import br.com.game_report.service.mapper.GameReportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GameReport}.
 */
@Service
@Transactional
public class GameReportService {

    private final Logger log = LoggerFactory.getLogger(GameReportService.class);

    private final GameReportRepository gameReportRepository;

    private final GameReportMapper gameReportMapper;

    public GameReportService(GameReportRepository gameReportRepository, GameReportMapper gameReportMapper) {
        this.gameReportRepository = gameReportRepository;
        this.gameReportMapper = gameReportMapper;
    }

    /**
     * Save a gameReport.
     *
     * @param gameReportDTO the entity to save.
     * @return the persisted entity.
     */
    public GameReportDTO save(GameReportDTO gameReportDTO) {
        log.debug("Request to save GameReport : {}", gameReportDTO);
        GameReport gameReport = gameReportMapper.toEntity(gameReportDTO);
        gameReport = gameReportRepository.save(gameReport);
        return gameReportMapper.toDto(gameReport);
    }

    /**
     * Update a gameReport.
     *
     * @param gameReportDTO the entity to save.
     * @return the persisted entity.
     */
    public GameReportDTO update(GameReportDTO gameReportDTO) {
        log.debug("Request to update GameReport : {}", gameReportDTO);
        GameReport gameReport = gameReportMapper.toEntity(gameReportDTO);
        gameReport = gameReportRepository.save(gameReport);
        return gameReportMapper.toDto(gameReport);
    }

    /**
     * Partially update a gameReport.
     *
     * @param gameReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GameReportDTO> partialUpdate(GameReportDTO gameReportDTO) {
        log.debug("Request to partially update GameReport : {}", gameReportDTO);

        return gameReportRepository
            .findById(gameReportDTO.getId())
            .map(existingGameReport -> {
                gameReportMapper.partialUpdate(existingGameReport, gameReportDTO);

                return existingGameReport;
            })
            .map(gameReportRepository::save)
            .map(gameReportMapper::toDto);
    }

    /**
     * Get all the gameReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GameReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GameReports");
        return gameReportRepository.findAll(pageable).map(gameReportMapper::toDto);
    }

    /**
     * Get one gameReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GameReportDTO> findOne(Long id) {
        log.debug("Request to get GameReport : {}", id);
        return gameReportRepository.findById(id).map(gameReportMapper::toDto);
    }

    /**
     * Delete the gameReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GameReport : {}", id);
        gameReportRepository.deleteById(id);
    }
}
