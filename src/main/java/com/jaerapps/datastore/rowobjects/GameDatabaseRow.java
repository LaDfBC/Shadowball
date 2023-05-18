package com.jaerapps.datastore.rowobjects;

import com.google.common.base.Objects;

import java.util.UUID;

/**
 * Represents a row of the database table "Game"
 */
public class GameDatabaseRow {
    private final UUID gameId;
    private final Integer sessionNumber;
    private final Integer seasonNumber;
    private final Boolean currentlyActive;

    public GameDatabaseRow(UUID gameId, Integer sessionNumber, Integer seasonNumber, Boolean currentlyActive) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDatabaseRow that = (GameDatabaseRow) o;
        return Objects.equal(gameId, that.gameId) && Objects.equal(sessionNumber, that.sessionNumber) && Objects.equal(seasonNumber, that.seasonNumber) && Objects.equal(currentlyActive, that.currentlyActive);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameId, sessionNumber, seasonNumber, currentlyActive);
    }
}
