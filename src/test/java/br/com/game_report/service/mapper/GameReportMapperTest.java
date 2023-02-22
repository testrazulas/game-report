package br.com.game_report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameReportMapperTest {

    private GameReportMapper gameReportMapper;

    @BeforeEach
    public void setUp() {
        gameReportMapper = new GameReportMapperImpl();
    }
}
