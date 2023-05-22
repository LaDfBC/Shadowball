package com.jaerapps.pojo;

import com.google.common.base.Objects;

import java.util.UUID;

public class PointsPojo {
    private final String memberId;
    private final String memberName;
    private final Integer difference;
    private final Integer points;

    private PointsPojo(String memberId, String memberName, Integer difference, Integer points) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.difference = difference;
        this.points = points;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public Integer getDifference() {
        return difference;
    }

    public Integer getPoints() {
        return points;
    }

    public static PointsPojo.Builder builder() {
        return new PointsPojo.Builder();
    }

    public static class Builder {
        private String memberId;
        private String memberName;
        private Integer difference;
        private Integer points;

        public Builder() {
        }

        public PointsPojo.Builder withMemberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public PointsPojo.Builder withMemberName(String memberName) {
            this.memberName = memberName;
            return this;
        }

        public PointsPojo.Builder withDifference(Integer difference) {
            this.difference = difference;
            return this;
        }

        public PointsPojo.Builder withPoints(Integer points) {
            this.points = points;
            return this;
        }

        public PointsPojo build() {
            return new PointsPojo(memberId, memberName, difference, points);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointsPojo that = (PointsPojo) o;
        return Objects.equal(memberId, that.memberId) && Objects.equal(memberName, that.memberName) &&
                Objects.equal(difference, that.difference) && Objects.equal(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId, memberName, difference, points);
    }
}
