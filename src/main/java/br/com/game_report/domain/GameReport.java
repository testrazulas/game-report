package br.com.game_report.domain;

import br.com.game_report.domain.enumeration.mvpResult;
import br.com.game_report.domain.enumeration.resultGame;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GameReport.
 */
@Entity
@Table(name = "game_report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "pokemon_name", nullable = false)
    private String pokemonName;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "lane", nullable = false)
    private String lane;

    @NotNull
    @Column(name = "game_date", nullable = false)
    private Instant gameDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private resultGame result;

    @Enumerated(EnumType.STRING)
    @Column(name = "mvp")
    private mvpResult mvp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPokemonName() {
        return this.pokemonName;
    }

    public GameReport pokemonName(String pokemonName) {
        this.setPokemonName(pokemonName);
        return this;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getType() {
        return this.type;
    }

    public GameReport type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLane() {
        return this.lane;
    }

    public GameReport lane(String lane) {
        this.setLane(lane);
        return this;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public Instant getGameDate() {
        return this.gameDate;
    }

    public GameReport gameDate(Instant gameDate) {
        this.setGameDate(gameDate);
        return this;
    }

    public void setGameDate(Instant gameDate) {
        this.gameDate = gameDate;
    }

    public resultGame getResult() {
        return this.result;
    }

    public GameReport result(resultGame result) {
        this.setResult(result);
        return this;
    }

    public void setResult(resultGame result) {
        this.result = result;
    }

    public mvpResult getMvp() {
        return this.mvp;
    }

    public GameReport mvp(mvpResult mvp) {
        this.setMvp(mvp);
        return this;
    }

    public void setMvp(mvpResult mvp) {
        this.mvp = mvp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameReport)) {
            return false;
        }
        return id != null && id.equals(((GameReport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameReport{" +
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
