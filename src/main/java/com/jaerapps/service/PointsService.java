package com.jaerapps.service;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.jaerapps.datastore.GuessDAO;
import com.jaerapps.pojo.GuessPojo;
import com.jaerapps.pojo.PointsPojo;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
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
        for (DifferenceToPointItem currentDifferential: differenceToPointList) {
            if (currentDifferential.maximumDifference > guess) {
                return currentDifferential.pointValue;
            }
        }

        return 0;
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
