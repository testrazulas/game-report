package br.com.game_report.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.game_report.IntegrationTest;
import br.com.game_report.domain.GameReport;
import br.com.game_report.domain.enumeration.mvpResult;
import br.com.game_report.domain.enumeration.resultGame;
import br.com.game_report.repository.GameReportRepository;
import br.com.game_report.service.dto.GameReportDTO;
import br.com.game_report.service.mapper.GameReportMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GameReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GameReportResourceIT {

    private static final String DEFAULT_POKEMON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POKEMON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LANE = "AAAAAAAAAA";
    private static final String UPDATED_LANE = "BBBBBBBBBB";

    private static final Instant DEFAULT_GAME_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GAME_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final resultGame DEFAULT_RESULT = resultGame.WIN;
    private static final resultGame UPDATED_RESULT = resultGame.LOST;

    private static final mvpResult DEFAULT_MVP = mvpResult.YES;
    private static final mvpResult UPDATED_MVP = mvpResult.NO;

    private static final String ENTITY_API_URL = "/api/game-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GameReportRepository gameReportRepository;

    @Autowired
    private GameReportMapper gameReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGameReportMockMvc;

    private GameReport gameReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameReport createEntity(EntityManager em) {
        GameReport gameReport = new GameReport()
            .pokemonName(DEFAULT_POKEMON_NAME)
            .type(DEFAULT_TYPE)
            .lane(DEFAULT_LANE)
            .gameDate(DEFAULT_GAME_DATE)
            .result(DEFAULT_RESULT)
            .mvp(DEFAULT_MVP);
        return gameReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameReport createUpdatedEntity(EntityManager em) {
        GameReport gameReport = new GameReport()
            .pokemonName(UPDATED_POKEMON_NAME)
            .type(UPDATED_TYPE)
            .lane(UPDATED_LANE)
            .gameDate(UPDATED_GAME_DATE)
            .result(UPDATED_RESULT)
            .mvp(UPDATED_MVP);
        return gameReport;
    }

    @BeforeEach
    public void initTest() {
        gameReport = createEntity(em);
    }

    @Test
    @Transactional
    void createGameReport() throws Exception {
        int databaseSizeBeforeCreate = gameReportRepository.findAll().size();
        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);
        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isCreated());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeCreate + 1);
        GameReport testGameReport = gameReportList.get(gameReportList.size() - 1);
        assertThat(testGameReport.getPokemonName()).isEqualTo(DEFAULT_POKEMON_NAME);
        assertThat(testGameReport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGameReport.getLane()).isEqualTo(DEFAULT_LANE);
        assertThat(testGameReport.getGameDate()).isEqualTo(DEFAULT_GAME_DATE);
        assertThat(testGameReport.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testGameReport.getMvp()).isEqualTo(DEFAULT_MVP);
    }

    @Test
    @Transactional
    void createGameReportWithExistingId() throws Exception {
        // Create the GameReport with an existing ID
        gameReport.setId(1L);
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        int databaseSizeBeforeCreate = gameReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPokemonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameReportRepository.findAll().size();
        // set the field null
        gameReport.setPokemonName(null);

        // Create the GameReport, which fails.
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isBadRequest());

        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameReportRepository.findAll().size();
        // set the field null
        gameReport.setType(null);

        // Create the GameReport, which fails.
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isBadRequest());

        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLaneIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameReportRepository.findAll().size();
        // set the field null
        gameReport.setLane(null);

        // Create the GameReport, which fails.
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isBadRequest());

        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGameDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameReportRepository.findAll().size();
        // set the field null
        gameReport.setGameDate(null);

        // Create the GameReport, which fails.
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        restGameReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isBadRequest());

        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGameReports() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        // Get all the gameReportList
        restGameReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].pokemonName").value(hasItem(DEFAULT_POKEMON_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].lane").value(hasItem(DEFAULT_LANE)))
            .andExpect(jsonPath("$.[*].gameDate").value(hasItem(DEFAULT_GAME_DATE.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].mvp").value(hasItem(DEFAULT_MVP.toString())));
    }

    @Test
    @Transactional
    void getGameReport() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        // Get the gameReport
        restGameReportMockMvc
            .perform(get(ENTITY_API_URL_ID, gameReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gameReport.getId().intValue()))
            .andExpect(jsonPath("$.pokemonName").value(DEFAULT_POKEMON_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.lane").value(DEFAULT_LANE))
            .andExpect(jsonPath("$.gameDate").value(DEFAULT_GAME_DATE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.mvp").value(DEFAULT_MVP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGameReport() throws Exception {
        // Get the gameReport
        restGameReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGameReport() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();

        // Update the gameReport
        GameReport updatedGameReport = gameReportRepository.findById(gameReport.getId()).get();
        // Disconnect from session so that the updates on updatedGameReport are not directly saved in db
        em.detach(updatedGameReport);
        updatedGameReport
            .pokemonName(UPDATED_POKEMON_NAME)
            .type(UPDATED_TYPE)
            .lane(UPDATED_LANE)
            .gameDate(UPDATED_GAME_DATE)
            .result(UPDATED_RESULT)
            .mvp(UPDATED_MVP);
        GameReportDTO gameReportDTO = gameReportMapper.toDto(updatedGameReport);

        restGameReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
        GameReport testGameReport = gameReportList.get(gameReportList.size() - 1);
        assertThat(testGameReport.getPokemonName()).isEqualTo(UPDATED_POKEMON_NAME);
        assertThat(testGameReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGameReport.getLane()).isEqualTo(UPDATED_LANE);
        assertThat(testGameReport.getGameDate()).isEqualTo(UPDATED_GAME_DATE);
        assertThat(testGameReport.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testGameReport.getMvp()).isEqualTo(UPDATED_MVP);
    }

    @Test
    @Transactional
    void putNonExistingGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gameReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gameReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGameReportWithPatch() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();

        // Update the gameReport using partial update
        GameReport partialUpdatedGameReport = new GameReport();
        partialUpdatedGameReport.setId(gameReport.getId());

        partialUpdatedGameReport.type(UPDATED_TYPE).lane(UPDATED_LANE).result(UPDATED_RESULT).mvp(UPDATED_MVP);

        restGameReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameReport))
            )
            .andExpect(status().isOk());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
        GameReport testGameReport = gameReportList.get(gameReportList.size() - 1);
        assertThat(testGameReport.getPokemonName()).isEqualTo(DEFAULT_POKEMON_NAME);
        assertThat(testGameReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGameReport.getLane()).isEqualTo(UPDATED_LANE);
        assertThat(testGameReport.getGameDate()).isEqualTo(DEFAULT_GAME_DATE);
        assertThat(testGameReport.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testGameReport.getMvp()).isEqualTo(UPDATED_MVP);
    }

    @Test
    @Transactional
    void fullUpdateGameReportWithPatch() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();

        // Update the gameReport using partial update
        GameReport partialUpdatedGameReport = new GameReport();
        partialUpdatedGameReport.setId(gameReport.getId());

        partialUpdatedGameReport
            .pokemonName(UPDATED_POKEMON_NAME)
            .type(UPDATED_TYPE)
            .lane(UPDATED_LANE)
            .gameDate(UPDATED_GAME_DATE)
            .result(UPDATED_RESULT)
            .mvp(UPDATED_MVP);

        restGameReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGameReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGameReport))
            )
            .andExpect(status().isOk());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
        GameReport testGameReport = gameReportList.get(gameReportList.size() - 1);
        assertThat(testGameReport.getPokemonName()).isEqualTo(UPDATED_POKEMON_NAME);
        assertThat(testGameReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGameReport.getLane()).isEqualTo(UPDATED_LANE);
        assertThat(testGameReport.getGameDate()).isEqualTo(UPDATED_GAME_DATE);
        assertThat(testGameReport.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testGameReport.getMvp()).isEqualTo(UPDATED_MVP);
    }

    @Test
    @Transactional
    void patchNonExistingGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gameReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGameReport() throws Exception {
        int databaseSizeBeforeUpdate = gameReportRepository.findAll().size();
        gameReport.setId(count.incrementAndGet());

        // Create the GameReport
        GameReportDTO gameReportDTO = gameReportMapper.toDto(gameReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGameReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gameReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GameReport in the database
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGameReport() throws Exception {
        // Initialize the database
        gameReportRepository.saveAndFlush(gameReport);

        int databaseSizeBeforeDelete = gameReportRepository.findAll().size();

        // Delete the gameReport
        restGameReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, gameReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameReport> gameReportList = gameReportRepository.findAll();
        assertThat(gameReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
