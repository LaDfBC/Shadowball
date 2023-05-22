package com.jaerapps.pojo;

import com.google.common.base.Objects;

import java.util.UUID;

public class GuessPojo {
    private final String memberId;
    private final UUID playId;
    private final Integer guessedNumber;
    private final Integer difference;
    private final String memberName;

    private GuessPojo(String memberId, UUID playId, Integer guessedNumber, Integer difference, String memberName) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String memberId;
        private UUID playId;
        private Integer guessedNumber;
        private Integer difference;
        private String memberName;

        public Builder() {
        }

        public GuessPojo.Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public GuessPojo.Builder withPlayId(UUID playId) {
            this.playId = playId;
            return this;
        }

        public GuessPojo.Builder withGuessedNumber(Integer guessedNumber) {
            this.guessedNumber = guessedNumber;
            return this;
        }


        public GuessPojo.Builder withDifference(Integer difference) {
            this.difference = difference;
            return this;
        }

        public GuessPojo.Builder withMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public GuessPojo build() {
            return new GuessPojo(memberId, playId, guessedNumber, difference, memberName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessPojo guessPojo = (GuessPojo) o;
        return Objects.equal(memberId, guessPojo.memberId) && Objects.equal(playId, guessPojo.playId) && Objects.equal(guessedNumber, guessPojo.guessedNumber) && Objects.equal(difference, guessPojo.difference) && Objects.equal(memberName, guessPojo.memberName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId, playId, guessedNumber, difference, memberName);
    }
}
