package com.jaerapps.datastore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jaerapps.generated.jooq.public_.tables.records.GuessRecord;
import com.jaerapps.pojo.DatabaseRowToPojoTranslator;
import com.jaerapps.pojo.GuessPojo;
import org.jooq.CloseableDSLContext;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jaerapps.generated.jooq.public_.Tables.GUESS;
import static com.jaerapps.guice.BasicModule.DATABASE_URL;
import static org.jooq.impl.DSL.*;

public class GuessDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuessDAO.class);

    private final String databaseUrl;

    @Inject
    public GuessDAO(@Named(DATABASE_URL) String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void insert(@Nonnull GuessPojo guessToInsert) throws DataAccessException {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .insertInto(GUESS)
                    .columns(GUESS.PLAY_ID, GUESS.MEMBER_ID, GUESS.MEMBER_NAME, GUESS.GUESSED_NUMBER)
                    .values(
                            guessToInsert.getPlayId(),
                            guessToInsert.getMemberId(),
                            guessToInsert.getMemberName(),
                            guessToInsert.getGuessedNumber()
                    )
                    .execute();
        }
    }

    public void update(@Nonnull GuessPojo guessPojo) throws DataAccessException {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .update(GUESS)
                    .set(GUESS.GUESSED_NUMBER, guessPojo.getGuessedNumber())
                    .where(GUESS.PLAY_ID.eq(guessPojo.getPlayId()))
                    .and(GUESS.MEMBER_ID.eq(guessPojo.getMemberId()))
                    .and(GUESS.MEMBER_NAME.eq(guessPojo.getMemberName()))
                    .execute();
        }
    }

    public Optional<GuessPojo> getByPlayAndMember(UUID playId, String memberId, String memberName) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            GuessRecord record = ctx
                    .selectFrom(GUESS)
                    .where(GUESS.PLAY_ID.eq(playId))
                    .and(GUESS.MEMBER_ID.eq(memberId))
                    .and(GUESS.MEMBER_NAME.eq(memberName))
                    .fetchOne();

            return DatabaseRowToPojoTranslator.guessFromRecord(record);
        }
    }

    //TODO: Test this!
    public void setDifferences(Integer pitchNumber, UUID playId) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            ctx
                    .update(GUESS)
                    .set(
                            GUESS.DIFFERENCE,
                            if_(
                                    GUESS.GUESSED_NUMBER.minus(pitchNumber).gt(0), // Absolute Value Check
                                    if_(
                                            // Choose the number that equals less than 500, i.e., the smaller one
                                            GUESS.GUESSED_NUMBER.minus(pitchNumber).gt(500),
                                            val(1000).minus(GUESS.GUESSED_NUMBER.minus(pitchNumber)),
                                            GUESS.GUESSED_NUMBER.minus(pitchNumber)
                                    ),
                                    if_(
                                            (GUESS.GUESSED_NUMBER.minus(pitchNumber).times(-1)).gt(500),
                                            val(1000).minus((GUESS.GUESSED_NUMBER.minus(pitchNumber).times(-1))),
                                            GUESS.GUESSED_NUMBER.minus(pitchNumber).times(-1)
                                    )
                            )
                    )
                    .where(GUESS.PLAY_ID.eq(playId))
                    .execute();
        }
    }

    public List<GuessPojo> getDifferenceSortedGuessesForPlay(UUID playId) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            Result<GuessRecord> guesses = ctx
                    .selectFrom(GUESS)
                    .where(GUESS.PLAY_ID.eq(playId))
                    .orderBy(GUESS.DIFFERENCE.asc())
                    .fetch();

            return DatabaseRowToPojoTranslator.guessListFromRecord(guesses);
        }
    }
}
