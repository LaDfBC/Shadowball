package com.jaerapps.pojo;

import com.jaerapps.generated.jooq.public_.tables.records.GameRecord;
import com.jaerapps.generated.jooq.public_.tables.records.GuessRecord;
import com.jaerapps.generated.jooq.public_.tables.records.PlayRecord;
import org.jooq.Record;
import org.jooq.Result;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jaerapps.generated.jooq.public_.Tables.*;


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

    public static List<FullGuessContextPojo> fullContextFromRecords(List<Record> allRecords) {
        return allRecords
                .stream()
                .map(record -> FullGuessContextPojo
                        .builder()
                        .withGame(
                                GamePojo
                                        .builder()
                                        .withGameId(record.get(GAME.GAME_ID))
                                        .withSeasonNumber(record.get(GAME.SEASON_NUMBER))
                                        .withSessionNumber(record.get(GAME.SESSION_NUMBER))
                                        .build())
                        .withPlay(
                                PlayPojo
                                        .builder()
                                        .withPlayId(record.get(PLAY.PLAY_ID))
                                        .withCreationDate(Date.from(record.get(PLAY.CREATION_DATE).toInstant()))
                                        .withPitchValue(record.get(PLAY.PITCH_VALUE))
                                        .withGameId(record.get(PLAY.GAME_ID))
                                        .build())
                        .withGuess(
                                GuessPojo
                                        .builder()
                                        .withPlayId(record.get(GUESS.PLAY_ID))
                                        .withGuessedNumber(record.get(GUESS.GUESSED_NUMBER))
                                        .withDifference(record.get(GUESS.DIFFERENCE))
                                        .withMemberName(record.get(GUESS.MEMBER_NAME))
                                        .withMemberId(record.get(GUESS.MEMBER_ID))
                                        .build())
                        .build())
                .collect(Collectors.toList());
    }
}
