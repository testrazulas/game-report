package br.com.game_report.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.game_report.domain.Player} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private String playerName;

    @NotNull
    private String rankPlayer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRankPlayer() {
        return rankPlayer;
    }

    public void setRankPlayer(String rankPlayer) {
        this.rankPlayer = rankPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerDTO)) {
            return false;
        }

        PlayerDTO playerDTO = (PlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, playerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerDTO{" +
            "id=" + getId() +
            ", playerName='" + getPlayerName() + "'" +
            ", rankPlayer='" + getRankPlayer() + "'" +
            "}";
    }
}
