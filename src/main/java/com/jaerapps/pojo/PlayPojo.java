package com.jaerapps.pojo;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.UUID;

public class PlayPojo {
    private final UUID playId;
    private final Integer pitchValue;
    private final Date creationDate;
    private final UUID gameId;

    private PlayPojo(UUID playId, Integer pitchValue, Date creationDate, UUID gameId) {
        this.playId = playId;
        this.pitchValue = pitchValue;
        this.creationDate = creationDate;
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

    public UUID getGameId() {
        return gameId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID playId;
        private Integer pitchValue;
        private Date creationDate;
        private UUID gameId;

        public Builder() {
        }

        public Builder withPlayId(UUID playId) {
            this.playId = playId;
            return this;
        }

        public Builder withPitchValue(Integer pitchValue) {
            this.pitchValue = pitchValue;
            return this;
        }

        public Builder withCreationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }


        public Builder withGameId(UUID gameId) {
            this.gameId = gameId;
            return this;
        }

        public PlayPojo build() {
            return new PlayPojo(playId, pitchValue, creationDate, gameId);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayPojo playPojo = (PlayPojo) o;
        return Objects.equal(playId, playPojo.playId) && Objects.equal(pitchValue, playPojo.pitchValue) && Objects.equal(creationDate, playPojo.creationDate) && Objects.equal(gameId, playPojo.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playId, pitchValue, creationDate, gameId);
    }
}
