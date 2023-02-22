package br.com.game_report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.game_report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GameReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameReport.class);
        GameReport gameReport1 = new GameReport();
        gameReport1.setId(1L);
        GameReport gameReport2 = new GameReport();
        gameReport2.setId(gameReport1.getId());
        assertThat(gameReport1).isEqualTo(gameReport2);
        gameReport2.setId(2L);
        assertThat(gameReport1).isNotEqualTo(gameReport2);
        gameReport1.setId(null);
        assertThat(gameReport1).isNotEqualTo(gameReport2);
    }
}
