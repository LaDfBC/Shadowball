package com.jaerapps.service;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.jaerapps.datastore.GuessDAO;
import com.jaerapps.pojo.GuessPojo;
import com.jaerapps.pojo.PointsPojo;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PointsService {
    private final GuessDAO guessDAO;

    private static List<DifferenceToPointItem> differenceToPointList; // Inclusive maximum difference to points that guess is worth

    @Inject
    public PointsService(@Nonnull final GuessDAO guessDAO) {
        this.guessDAO = guessDAO;

        // This may need to be database edit-able
        differenceToPointList = List.of(
                new DifferenceToPointItem(20, 6),
                new DifferenceToPointItem(40, 5),
                new DifferenceToPointItem(60, 4),
                new DifferenceToPointItem(100, 3),
                new DifferenceToPointItem(150, 2),
                new DifferenceToPointItem(200, 1),
                new DifferenceToPointItem(500, 0)
        );
    }

    //TODO: INJECT BONUS POINTS INTO HERE.  NEW DAO METHOD, THEN TACK ON THE BONUS POINTS
    public List<PointsPojo> fetchSortedGuessesByPlay(UUID playId) {
        List<GuessPojo> guesses = guessDAO.getDifferenceSortedGuessesForPlay(playId);
        return guessesToPoints(guesses);
    }

    private List<PointsPojo> guessesToPoints(List<GuessPojo> guesses) {
        return guesses
                .stream()
                .map(guess -> PointsPojo
                        .builder()
                        .withDifference(guess.getDifference())
                        .withMemberId(guess.getMemberId())
                        .withMemberName(guess.getMemberName())
                        .withPoints(getPointsForGuess(guess.getDifference()))
                        .build())
                .collect(Collectors.toList());
    }

    private Integer getPointsForGuess(Integer guess) {
        if (guess == null) {
            return 0;
        }

        for (DifferenceToPointItem currentDifferential: differenceToPointList) {
            if (currentDifferential.maximumDifference > guess) {
                return currentDifferential.pointValue;
            }
        }

        return 0;
    }

    public List<PointsPojo> fetchSortedPointsLeaders(@Nullable Integer seasonNumber, @Nullable Integer sessionNumber) {
        List<GuessPojo> guesses;
        if (seasonNumber == null && sessionNumber == null) {
            guesses = guessDAO.fetchAll();
        } else if (sessionNumber == null) {
            guesses = guessDAO.fetchForSeason(seasonNumber);
        } else {
            guesses = guessDAO.fetchForGame(seasonNumber, sessionNumber);
        }

        Map<String, Integer> pointsPerMember = Maps.newHashMap();

        guesses.forEach(guessPojo -> {
            if(pointsPerMember.containsKey(guessPojo.getMemberName())) {
                pointsPerMember.put(
                        guessPojo.getMemberName(),
                        pointsPerMember.get(guessPojo.getMemberName()) + getPointsForGuess(guessPojo.getDifference()));
            } else {
                pointsPerMember.put(guessPojo.getMemberName(), getPointsForGuess(guessPojo.getDifference()));
            }
        });


        return pointsPerMember
                .entrySet()
                .stream()
                .map(
                        memberNameToPoints -> PointsPojo
                                .builder()
                                .withMemberName(memberNameToPoints.getKey())
                                .withPoints(memberNameToPoints.getValue())
                                .build()
                )
                .sorted((p1, p2) -> p2.getPoints().compareTo(p1.getPoints()))
                .collect(Collectors.toList());
    }

    private static class DifferenceToPointItem {
        private final int maximumDifference;
        private final int pointValue;

        public DifferenceToPointItem(int maximumDifference, int pointValue) {
            this.maximumDifference = maximumDifference;
            this.pointValue = pointValue;
        }

        public int getMaximumDifference() {
            return maximumDifference;
        }

        public int getPointValue() {
            return pointValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DifferenceToPointItem that = (DifferenceToPointItem) o;
            return maximumDifference == that.maximumDifference && pointValue == that.pointValue;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(maximumDifference, pointValue);
        }
    }
}
