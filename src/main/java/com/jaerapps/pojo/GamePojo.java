package com.jaerapps.pojo;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.UUID;

public class GamePojo {
    private final UUID gameId;
    private final Integer sessionNumber;
    private final Integer seasonNumber;
    private final Boolean currentlyActive;

    private GamePojo(UUID gameId, Integer sessionNumber, Integer seasonNumber, Boolean currentlyActive) {
        this.gameId = gameId;
        this.sessionNumber = sessionNumber;
        this.seasonNumber = seasonNumber;
        this.currentlyActive = currentlyActive;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public Boolean getCurrentlyActive() {
        return currentlyActive;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID gameId;
        private Integer sessionNumber;
        private Integer seasonNumber;
        private Boolean currentlyActive;

        public Builder() {
        }

        public Builder withGameId(UUID gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder withSessionNumber(Integer sessionNumber) {
            this.sessionNumber = sessionNumber;
            return this;
        }

        public Builder withSeasonNumber(Integer seasonNumber) {
            this.seasonNumber = seasonNumber;
            return this;
        }


        public Builder isCurrentlyActive(Boolean currentlyActive) {
            this.currentlyActive = currentlyActive;
            return this;
        }

        public GamePojo build() {
            return new GamePojo(gameId, sessionNumber, seasonNumber, currentlyActive);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePojo gamePojo = (GamePojo) o;
        return Objects.equal(gameId, gamePojo.gameId) && Objects.equal(sessionNumber, gamePojo.sessionNumber) && Objects.equal(seasonNumber, gamePojo.seasonNumber) && Objects.equal(currentlyActive, gamePojo.currentlyActive);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameId, sessionNumber, seasonNumber, currentlyActive);
    }
}
