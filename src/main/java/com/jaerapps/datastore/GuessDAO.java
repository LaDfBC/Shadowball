package com.jaerapps.datastore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jaerapps.generated.jooq.public_.tables.records.GuessRecord;
import com.jaerapps.pojo.DatabaseRowToPojoTranslator;
import com.jaerapps.pojo.GuessPojo;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jaerapps.generated.jooq.public_.Tables.*;
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
                    .columns(GUESS.PLAY_ID, GUESS.MEMBER_ID, GUESS.MEMBER_NAME, GUESS.GUESSED_NUMBER, GUESS.DIFFERENCE)
                    .values(
                            guessToInsert.getPlayId(),
                            guessToInsert.getMemberId(),
                            guessToInsert.getMemberName(),
                            guessToInsert.getGuessedNumber(),
                            guessToInsert.getDifference()
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

    public List<GuessPojo> fetchAll() {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            Result<GuessRecord> guesses = ctx
                    .selectFrom(GUESS)
                    .fetch();

            return DatabaseRowToPojoTranslator.guessListFromRecord(guesses);
        }
    }

    public List<GuessPojo> fetchForSeason(Integer seasonNumber) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            Result<GuessRecord> guesses = ctx
                    .select(GUESS.asterisk())
                    .from(GUESS)
                    .join(PLAY)
                    .on(GUESS.PLAY_ID.eq(PLAY.PLAY_ID))
                    .join(GAME)
                    .on(GAME.GAME_ID.eq(PLAY.GAME_ID))
                    .where(GAME.SEASON_NUMBER.eq(seasonNumber))
                    .fetchInto(GUESS);

            return DatabaseRowToPojoTranslator.guessListFromRecord(guesses);
        }
    }

    public List<GuessPojo> fetchForGame(Integer seasonNumber, Integer sessionNumber) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            Result<GuessRecord> guesses = ctx
                    .select(GUESS.asterisk())
                    .from(GUESS)
                    .join(PLAY)
                    .on(GUESS.PLAY_ID.eq(PLAY.PLAY_ID))
                    .join(GAME)
                    .on(GAME.GAME_ID.eq(PLAY.GAME_ID))
                    .where(GAME.SEASON_NUMBER.eq(seasonNumber))
                    .and(GAME.SESSION_NUMBER.eq(sessionNumber))
                    .fetchInto(GUESS);

            return DatabaseRowToPojoTranslator.guessListFromRecord(guesses);
        }
    }

    public int fetchStreak(GuessPojo guess) {
        try (CloseableDSLContext ctx = DSL.using(databaseUrl)) {
            CommonTableExpression<Record4<UUID, OffsetDateTime, String, Integer>> previous_guesses =
                    name("previous_guesses")
                            .as(
                                    ctx.select(PLAY.PLAY_ID, PLAY.CREATION_DATE, GUESS.MEMBER_NAME, GUESS.DIFFERENCE)
                                            .from(GUESS)
                                            .join(PLAY)
                                            .on(GUESS.PLAY_ID.eq(PLAY.PLAY_ID))
                                            .orderBy(GUESS.MEMBER_NAME, PLAY.CREATION_DATE)
                            );

            CommonTableExpression<Record5<UUID, OffsetDateTime, String, Integer, Integer>> partitioned_guesses =
                    name("partitioned_guesses").fields("play_id", "creation_date", "member_name", "difference", "row_number")
                            .as(
                                    ctx
                                            .with(previous_guesses)
                                            .select(
                                                    previous_guesses.field(PLAY.PLAY_ID),
                                                    previous_guesses.field(PLAY.CREATION_DATE),
                                                    previous_guesses.field(GUESS.MEMBER_NAME),
                                                    previous_guesses.field(GUESS.DIFFERENCE),
                                                    rowNumber().over(
                                                            partitionBy(previous_guesses.field(GUESS.MEMBER_NAME))
                                                                    .orderBy(previous_guesses.field(PLAY.CREATION_DATE)))
                                            )
                                            .from(previous_guesses)
                            );

            Table<Record5<UUID, OffsetDateTime, String, Integer, Integer>> pgs_prev = partitioned_guesses.as("pgs_prev");
            Table<Record5<UUID, OffsetDateTime, String, Integer, Integer>> pgs = partitioned_guesses.as("pgs");

//            case
//                    when pgs.difference > 200 THEN 0
//                WHEN max(pgs_prev.row_number) is null THEN
//            case when pgs.difference > 200 THEN 0
//                ELSE pgs.row_number
//                    END
//                ELSE (pgs.row_number - max(pgs_prev.row_number))
//                END

            return ctx
                    .with(previous_guesses)
                    .with(partitioned_guesses)
                    .select(
                            case_()
                                    .when(pgs.field("difference", GUESS.DIFFERENCE.getType()).gt(200), 0)
                                    .when(max(pgs_prev.field("row_number", Integer.class)).isNull(),
                                            case_()
                                                    .when(pgs.field("difference", GUESS.DIFFERENCE.getType()).gt(200), 0)
                                                    .else_(pgs.field("row_number", Integer.class))
                                    )
                                    .else_(pgs.field("row_number", Integer.class).sub(max(pgs_prev.field("row_number", Integer.class))))
                                    .as("streak")
                    )
                    .from(pgs)
                    .leftJoin(pgs_prev)
                    .on(pgs.field("member_name", GUESS.MEMBER_NAME.getType())
                            .eq(pgs_prev.field("member_name", GUESS.MEMBER_NAME.getType())))
                    .and(pgs_prev.field("row_number", Integer.class).lt(pgs.field("row_number", Integer.class)))
                    .and(pgs_prev.field("difference", GUESS.DIFFERENCE.getType()).gt(200))
                    .where(pgs.field("member_name", GUESS.MEMBER_NAME.getType()).eq(guess.getMemberName())
                            .and(pgs.field("play_id", PLAY.PLAY_ID.getType()).eq(guess.getPlayId())))
                    .groupBy(pgs.field("difference", GUESS.DIFFERENCE.getType()), pgs.field("row_number", Integer.class))
                    .fetchOne()
                    .get("streak", Integer.class);
        } catch (NullPointerException npe) {
            System.out.println("NPE - What's wrong?");
            throw npe;
        }

//        select
//        case
//                when pgs.difference > 200 THEN 0
//        ELSE (pgs.row_number - max(pgs_prev.row_number))
//        END as streak,
//                pgs.member_name,
//                pgs.play_id,
//                pgs.row_number,
//                pgs.difference
//        from partitioned_guesses pgs
//        join partitioned_guesses pgs_prev
//        on pgs.member_name = pgs_prev.member_name
//        and pgs_prev.row_number < pgs.row_number
//        and pgs_prev.difference > 200
//        where pgs.member_name = 'xxx' and pgs.play_id = 'yyy'
//        group by 2, 3, 4, 5
//        order by pgs.member_name, pgs.row_number;
//        return 0;
    }
}
