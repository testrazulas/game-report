package br.com.game_report.service.dto;

import br.com.game_report.domain.enumeration.mvpResult;
import br.com.game_report.domain.enumeration.resultGame;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.game_report.domain.GameReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameReportDTO implements Serializable {

    private Long id;

    @NotNull
    private String pokemonName;

    @NotNull
    private String type;

    @NotNull
    private String lane;

    @NotNull
    private Instant gameDate;

    private resultGame result;

    private mvpResult mvp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public Instant getGameDate() {
        return gameDate;
    }

    public void setGameDate(Instant gameDate) {
        this.gameDate = gameDate;
    }

    public resultGame getResult() {
        return result;
    }

    public void setResult(resultGame result) {
        this.result = result;
    }

    public mvpResult getMvp() {
        return mvp;
    }

    public void setMvp(mvpResult mvp) {
        this.mvp = mvp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameReportDTO)) {
            return false;
        }

        GameReportDTO gameReportDTO = (GameReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameReportDTO{" +
            "id=" + getId() +
            ", pokemonName='" + getPokemonName() + "'" +
            ", type='" + getType() + "'" +
            ", lane='" + getLane() + "'" +
            ", gameDate='" + getGameDate() + "'" +
            ", result='" + getResult() + "'" +
            ", mvp='" + getMvp() + "'" +
            "}";
    }
}
