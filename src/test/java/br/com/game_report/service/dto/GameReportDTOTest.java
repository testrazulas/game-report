package br.com.game_report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.game_report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GameReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameReportDTO.class);
        GameReportDTO gameReportDTO1 = new GameReportDTO();
        gameReportDTO1.setId(1L);
        GameReportDTO gameReportDTO2 = new GameReportDTO();
        assertThat(gameReportDTO1).isNotEqualTo(gameReportDTO2);
        gameReportDTO2.setId(gameReportDTO1.getId());
        assertThat(gameReportDTO1).isEqualTo(gameReportDTO2);
        gameReportDTO2.setId(2L);
        assertThat(gameReportDTO1).isNotEqualTo(gameReportDTO2);
        gameReportDTO1.setId(null);
        assertThat(gameReportDTO1).isNotEqualTo(gameReportDTO2);
    }
}
