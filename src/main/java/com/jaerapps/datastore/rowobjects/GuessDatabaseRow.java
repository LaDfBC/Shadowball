package com.jaerapps.datastore.rowobjects;

import com.google.common.base.Objects;

import java.util.UUID;

/**
 * Represents a row of the database table "Guess"
 */
public class GuessDatabaseRow {
    private final String memberId;
    private final UUID playId;
    private final Integer guessedNumber;
    private final Integer difference;
    private final String memberName;

    public GuessDatabaseRow(String memberId, UUID playId, Integer guessedNumber, Integer difference, String memberName) {
        this.memberId = memberId;
        this.playId = playId;
        this.guessedNumber = guessedNumber;
        this.difference = difference;
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public UUID getPlayId() {
        return playId;
    }

    public Integer getGuessedNumber() {
        return guessedNumber;
    }

    public Integer getDifference() {
        return difference;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessDatabaseRow that = (GuessDatabaseRow) o;
        return Objects.equal(memberId, that.memberId) && Objects.equal(playId, that.playId) && Objects.equal(guessedNumber, that.guessedNumber) && Objects.equal(difference, that.difference) && Objects.equal(memberName, that.memberName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId, playId, guessedNumber, difference, memberName);
    }
}
