package com.jaerapps.pojo;

import com.jaerapps.generated.jooq.public_.tables.Play;
import com.jaerapps.generated.jooq.public_.tables.records.GameRecord;
import com.jaerapps.generated.jooq.public_.tables.records.GuessRecord;
import com.jaerapps.generated.jooq.public_.tables.records.PlayRecord;
import org.jooq.Result;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatabaseRowToPojoTranslator {
    public static Optional<PlayPojo> playFromRecord(@Nullable PlayRecord playFromDatabase) {
        return playFromDatabase == null ? Optional.empty() : Optional.of(PlayPojo.builder()
                .withPlayId(playFromDatabase.getPlayId())
                .withCreationDate(Date.from(playFromDatabase.getCreationDate().toInstant()))
                .withPitchValue(playFromDatabase.getPitchValue())
                .withGameId(playFromDatabase.getGameId())
                .build());
    }

    public static Optional<GamePojo> gameFromRecord(@Nullable GameRecord gameFromDatabase) {
        return gameFromDatabase == null ? Optional.empty() : Optional.of(GamePojo.builder()
                .withGameId(gameFromDatabase.getGameId())
                .withSeasonNumber(gameFromDatabase.getSeasonNumber())
                .withSessionNumber(gameFromDatabase.getSeasonNumber())
                .isCurrentlyActive(gameFromDatabase.getCurrentlyActive())
                .build());
    }

    public static Optional<GuessPojo> guessFromRecord(@Nullable GuessRecord guessFromDatabase) {
        return guessFromDatabase == null ? Optional.empty() : Optional.of(GuessPojo.builder()
                .withPlayId(guessFromDatabase.getPlayId())
                .withMemberId(guessFromDatabase.getMemberId())
                .withMemberName(guessFromDatabase.getMemberName())
                .withGuessedNumber(guessFromDatabase.getGuessedNumber())
                .withDifference(guessFromDatabase.getDifference())
                .build()
        );
    }

    public static List<GuessPojo> guessListFromRecord(@Nonnull Result<GuessRecord> guesses) {
        return guesses
                .stream()
                .map(guessRecord -> {
                    //noinspection OptionalGetWithoutIsPresent -- We can safely ignore since it's a list of results
                    return guessFromRecord(guessRecord).get();
                })
                .collect(Collectors.toList());
    }
}
