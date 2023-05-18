package com.jaerapps.datastore.rowobjects;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a row of the database table "Play"
 */
public class PlayDatabaseRow {
    private final UUID playId;
    private final Integer pitchValue;
    private final Date creationDate;
    private final String serverId;
    private final UUID gameId;

    public PlayDatabaseRow(UUID playId, Integer pitchValue, Date creationDate, String serverId, UUID gameId) {
        this.playId = playId;
        this.pitchValue = pitchValue;
        this.creationDate = creationDate;
        this.serverId = serverId;
        this.gameId = gameId;
    }

    public UUID getPlayId() {
        return playId;
    }

    public Integer getPitchValue() {
        return pitchValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getServerId() {
        return serverId;
    }

    public UUID getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayDatabaseRow that = (PlayDatabaseRow) o;
        return Objects.equal(playId, that.playId) && Objects.equal(pitchValue, that.pitchValue) && Objects.equal(creationDate, that.creationDate) && Objects.equal(serverId, that.serverId) && Objects.equal(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playId, pitchValue, creationDate, serverId, gameId);
    }
}
