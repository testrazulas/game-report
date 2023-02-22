package br.com.game_report.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "player_name", nullable = false)
    private String playerName;

    @NotNull
    @Column(name = "rank_player", nullable = false)
    private String rankPlayer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Player playerName(String playerName) {
        this.setPlayerName(playerName);
        return this;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRankPlayer() {
        return this.rankPlayer;
    }

    public Player rankPlayer(String rankPlayer) {
        this.setRankPlayer(rankPlayer);
        return this;
    }

    public void setRankPlayer(String rankPlayer) {
        this.rankPlayer = rankPlayer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", playerName='" + getPlayerName() + "'" +
            ", rankPlayer='" + getRankPlayer() + "'" +
            "}";
    }
}
