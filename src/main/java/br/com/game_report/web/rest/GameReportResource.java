package br.com.game_report.web.rest;

import br.com.game_report.repository.GameReportRepository;
import br.com.game_report.service.GameReportService;
import br.com.game_report.service.dto.GameReportDTO;
import br.com.game_report.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.game_report.domain.GameReport}.
 */
@RestController
@RequestMapping("/api")
public class GameReportResource {

    private final Logger log = LoggerFactory.getLogger(GameReportResource.class);

    private static final String ENTITY_NAME = "gameReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameReportService gameReportService;

    private final GameReportRepository gameReportRepository;

    public GameReportResource(GameReportService gameReportService, GameReportRepository gameReportRepository) {
        this.gameReportService = gameReportService;
        this.gameReportRepository = gameReportRepository;
    }

    /**
     * {@code POST  /game-reports} : Create a new gameReport.
     *
     * @param gameReportDTO the gameReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gameReportDTO, or with status {@code 400 (Bad Request)} if the gameReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/game-reports")
    public ResponseEntity<GameReportDTO> createGameReport(@Valid @RequestBody GameReportDTO gameReportDTO) throws URISyntaxException {
        log.debug("REST request to save GameReport : {}", gameReportDTO);
        if (gameReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new gameReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GameReportDTO result = gameReportService.save(gameReportDTO);
        return ResponseEntity
            .created(new URI("/api/game-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /game-reports/:id} : Updates an existing gameReport.
     *
     * @param id the id of the gameReportDTO to save.
     * @param gameReportDTO the gameReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameReportDTO,
     * or with status {@code 400 (Bad Request)} if the gameReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gameReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/game-reports/{id}")
    public ResponseEntity<GameReportDTO> updateGameReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GameReportDTO gameReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GameReport : {}, {}", id, gameReportDTO);
        if (gameReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GameReportDTO result = gameReportService.update(gameReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /game-reports/:id} : Partial updates given fields of an existing gameReport, field will ignore if it is null
     *
     * @param id the id of the gameReportDTO to save.
     * @param gameReportDTO the gameReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gameReportDTO,
     * or with status {@code 400 (Bad Request)} if the gameReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gameReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gameReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/game-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GameReportDTO> partialUpdateGameReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GameReportDTO gameReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GameReport partially : {}, {}", id, gameReportDTO);
        if (gameReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gameReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gameReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GameReportDTO> result = gameReportService.partialUpdate(gameReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gameReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /game-reports} : get all the gameReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gameReports in body.
     */
    @GetMapping("/game-reports")
    public ResponseEntity<List<GameReportDTO>> getAllGameReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GameReports");
        Page<GameReportDTO> page = gameReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /game-reports/:id} : get the "id" gameReport.
     *
     * @param id the id of the gameReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gameReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/game-reports/{id}")
    public ResponseEntity<GameReportDTO> getGameReport(@PathVariable Long id) {
        log.debug("REST request to get GameReport : {}", id);
        Optional<GameReportDTO> gameReportDTO = gameReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gameReportDTO);
    }

    /**
     * {@code DELETE  /game-reports/:id} : delete the "id" gameReport.
     *
     * @param id the id of the gameReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/game-reports/{id}")
    public ResponseEntity<Void> deleteGameReport(@PathVariable Long id) {
        log.debug("REST request to delete GameReport : {}", id);
        gameReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
